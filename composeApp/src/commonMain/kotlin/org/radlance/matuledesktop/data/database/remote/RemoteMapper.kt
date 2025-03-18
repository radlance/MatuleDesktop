package org.radlance.matuledesktop.data.database.remote

import org.radlance.matuledesktop.data.database.remote.entity.RemoteCategoryEntity
import org.radlance.matuledesktop.data.database.remote.entity.RemoteProductEntity
import org.radlance.matuledesktop.data.database.remote.entity.UserEntity
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.domain.product.Category
import org.radlance.matuledesktop.domain.product.Product

abstract class RemoteMapper {
    protected fun RemoteCategoryEntity.toCategory(): Category {
        return Category(id = id, title = title)
    }

    protected fun RemoteProductEntity.toProduct(
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
            isPopular = isPopular
        )
    }


    protected fun UserEntity.toUser(): User {
        return User(firstName = name, imageUrl = imageUrl)
    }
}
