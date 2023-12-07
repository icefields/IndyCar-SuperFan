package org.hungrytessy.indycarsuperfan.presentation.adapters

import org.hungrytessy.indycarsuperfan.domain.model.Driver


interface OnDriverClickListener {
    fun onDriverClick(driver: Driver?)
}