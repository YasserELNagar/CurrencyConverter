package com.yasser.currencyconverter.data._common.util.mapper

import com.yasser.currencyconverter.data.currency.local.CurrencySymbolLocalEntity

class CurrencySymbolMapper : Mapper<CurrencySymbolLocalEntity,Pair<String,String>>{


    override fun fromDomain(domain: Map.Entry<String, String>): CurrencySymbolLocalEntity {
        return CurrencySymbolLocalEntity(domain.hashCode(),domain.key,domain.value)
    }

    override fun fromEntity(entity: CurrencySymbolLocalEntity): Pair<String, String> {
        return Pair(entity.abbreviation,entity.name)
    }

}