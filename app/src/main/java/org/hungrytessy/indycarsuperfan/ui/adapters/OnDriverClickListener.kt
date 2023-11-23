package org.hungrytessy.indycarsuperfan.ui.adapters

import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver

interface OnDriverClickListener {
    fun onDriverClick(driver: Driver?)
}