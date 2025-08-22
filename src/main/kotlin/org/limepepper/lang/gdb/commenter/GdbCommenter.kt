package org.limepepper.lang.gdb.commenter

import com.intellij.lang.Commenter

class GdbCommenter : Commenter {
    override fun getLineCommentPrefix(): String? {
        return "#";
    }

    override fun getBlockCommentPrefix(): String? {
        return "";
    }

    override fun getBlockCommentSuffix(): String? {
        return null;
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return null;
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null;
    }

}
