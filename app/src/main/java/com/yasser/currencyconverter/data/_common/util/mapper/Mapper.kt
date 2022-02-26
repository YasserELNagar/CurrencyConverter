package com.yasser.currencyconverter.data._common.util.mapper

interface Mapper<Entity,Domain> {

    fun fromEntity(entity:Entity):Domain

    fun fromDomain(domain: Map.Entry<String, String>): Entity

}