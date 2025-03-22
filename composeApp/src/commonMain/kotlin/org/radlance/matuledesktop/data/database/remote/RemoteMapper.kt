package org.radlance.matuledesktop.data.database.remote

import org.radlance.matuledesktop.data.database.remote.entity.BrandEntity
import org.radlance.matuledesktop.data.database.remote.entity.CategoryEntity
import org.radlance.matuledesktop.data.database.remote.entity.OriginCountryEntity
import org.radlance.matuledesktop.data.database.remote.entity.ProductEntity
import org.radlance.matuledesktop.data.database.remote.entity.UserEntity
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.domain.product.Brand
import org.radlance.matuledesktop.domain.product.Category
import org.radlance.matuledesktop.domain.product.OriginCountry
import org.radlance.matuledesktop.domain.product.Product

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
            brandId = brandId
        )
    }

    protected fun OriginCountryEntity.toOriginCountry(): OriginCountry {
        return OriginCountry(id = id, name = name)
    }

    protected fun BrandEntity.toBrand(): Brand {
        return Brand(id = id, name = name)
    }


    protected fun UserEntity.toUser(): User {
        return User(firstName = name, imageUrl = imageUrl)
    }
}
