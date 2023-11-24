package org.hungrytessy.indycarsuperfan.presentation.adapters

import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver

interface OnDriverClickListener {
    fun onDriverClick(driver: Driver?)
}