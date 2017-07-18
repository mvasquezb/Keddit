@file:JvmName("TimeUtils")

package com.pmvb.keddit.commons.extensions

import java.util.Date
import org.ocpsoft.prettytime.PrettyTime

fun Long.friendlyTime(): String {
    return PrettyTime().format(Date(this))
}