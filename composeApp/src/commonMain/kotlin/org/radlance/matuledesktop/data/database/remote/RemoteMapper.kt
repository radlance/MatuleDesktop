package org.radlance.matuledesktop.data.database.remote

import org.radlance.matuledesktop.data.database.remote.entity.BrandEntity
import org.radlance.matuledesktop.data.database.remote.entity.CategoryEntity
import org.radlance.matuledesktop.data.database.remote.entity.ClaspTypeEntity
import org.radlance.matuledesktop.data.database.remote.entity.OriginCountryEntity
import org.radlance.matuledesktop.data.database.remote.entity.ProductEntity
import org.radlance.matuledesktop.data.database.remote.entity.ProductSizeEntity
import org.radlance.matuledesktop.data.database.remote.entity.SizeEntity
import org.radlance.matuledesktop.domain.product.Brand
import org.radlance.matuledesktop.domain.product.Category
import org.radlance.matuledesktop.domain.product.ClaspType
import org.radlance.matuledesktop.domain.product.OriginCountry
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.domain.product.ProductSize
import org.radlance.matuledesktop.domain.product.Size

abstract class RemoteMapper {
    protected fun CategoryEntity.toCategory(): Category {
        return Category(id = id, title = title)
    }

    protected fun ProductEntity.toProduct(
        isFavorite: Boolean,
        quantityInCart: Int
    ): Product {
        return Product(
            id = id,
            title = title,
            price = price,
            description = description,
            imageUrl = imageUrl,
            isFavorite = isFavorite,
            quantityInCart = quantityInCart,
            categoryId = categoryId,
            isPopular = isPopular,
            originCountryId = originCountryId,
            sizes = sizes.map { it.toProductSize() },
            brandId = brandId,
            claspTypeId = claspTypeId,
            modelName = modelName
        )
    }

    protected fun ProductSizeEntity.toProductSize(): ProductSize {
        return ProductSize(size = size, quantity = quantity)
    }

    protected fun SizeEntity.toSize(): Size {
        return Size(id = id, number = number)
    }

    protected fun OriginCountryEntity.toOriginCountry(): OriginCountry {
        return OriginCountry(id = id, name = name)
    }

    protected fun BrandEntity.toBrand(): Brand {
        return Brand(id = id, name = name)
    }

    protected fun ClaspTypeEntity.toClaspType(): ClaspType {
        return ClaspType(id = id, name = name)
    }
}
