package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.PropertyDao
import com.global.tenantrix.data.entity.PropertyEntity
import javax.inject.Inject

class PropertyRepository @Inject constructor(
    private val propertyDao: PropertyDao
)  {

    fun getAllProperties() = propertyDao.getAllProperties()

    suspend fun addProperty(property: PropertyEntity): Long {
        return propertyDao.insertProperty(property)
    }

}
