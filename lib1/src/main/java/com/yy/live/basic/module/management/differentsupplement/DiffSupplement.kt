package com.yy.live.basic.module.management.differentsupplement

import java.util.LinkedList

/**
 * module 的差异参数
 *
 * @author Abel Joo http://abeljoo.github.io/
 * @Date: 2018/8/9
 * @Email: zhujiajun@yy.com
 * @YY: 909019111
 */
class DiffSupplement(append: List<String>, remove: List<String>) {
    private var append = LinkedList<String>()
    private var remove = LinkedList<String>()

    init {
        this.append.addAll(append)
        this.remove.addAll(remove)
    }

    fun getAppend(): List<String> {
        return append
    }

    fun getRemove(): List<String> {
        return remove
    }
}
