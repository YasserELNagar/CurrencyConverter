package com.yasser.currencyconverter.data._common.util.mapper

/**
 *Created by Yasser.Elnagar on 27/02/2022
 */
interface Mapper<Entity,Domain> {

    fun fromEntity(entity:Entity):Domain

    fun fromDomain(domain: Map.Entry<String, String>): Entity

}