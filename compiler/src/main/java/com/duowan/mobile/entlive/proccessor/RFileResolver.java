package com.duowan.mobile.entlive.proccessor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by 张宇 on 2018/10/2.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 * <p>
 * 对Library的R文件进行操作，把资源id的数值映射回 "R.XX.XXX" 这样的代码
 */
@SuppressWarnings("WeakerAccess")
public class RFileResolver {

    //map "layout" ->  0x12314123 -> "layout_activity_main"
    private Map<String, Map<Integer, String>> R = new LinkedHashMap<>();

    public RFileResolver(String rFilePath) throws Throwable {
        this(new File(rFilePath));
    }

    public RFileResolver(File rFile) throws Throwable {
        CompilationUnit compilationUnit = JavaParser.parse(rFile);
        TypeDeclaration resourceClass = compilationUnit.getTypes().get(0);
        for (Node node : resourceClass.getChildNodes()) {
            if (node instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration cls = (ClassOrInterfaceDeclaration) node;
                String resClassify = cls.getNameAsString();

                Map<Integer, String> idToName = new LinkedHashMap<>();

                for (BodyDeclaration field : cls.getMembers()) {
                    if (field instanceof FieldDeclaration) {
                        FieldDeclaration declaration = (FieldDeclaration) field;
                        // Check that the field is an Int because styleable also contains Int arrays which can't be
                        // used in annotations.
                        if (isInt(declaration)) {
                            VariableDeclarator variable = declaration.getVariables().get(0);
                            final String fieldName = variable.getNameAsString();
                            String fieldValue = variable.getInitializer()
                                    .map(new Function<Expression, String>() {
                                        @Override
                                        public String apply(Expression expression) {
                                            return expression.toString();
                                        }
                                    })
                                    .orElseThrow(new Supplier<Throwable>() {
                                        @Override
                                        public Throwable get() {
                                            return new IllegalStateException("Field " + fieldName + " missing initializer");
                                        }
                                    });

                            try {
                                if (fieldValue.startsWith("0x")) {
                                    fieldValue = fieldValue.substring(2);
                                    Integer resId = Integer.parseInt(fieldValue, 16);
                                    idToName.put(resId, fieldName);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("error " + e);
                            }
                        }
                    }
                }
                R.put(resClassify, idToName);
            }
        }
    }

    public String getFieldName(String classify, int resId) {
        return R.get(classify).get(resId);
    }

    private static boolean isInt(FieldDeclaration field) {
        Type type = field.getCommonType();
        return type instanceof PrimitiveType
                && ((PrimitiveType) type).getType() == PrimitiveType.Primitive.INT;
    }
}
