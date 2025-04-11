package org.radlance.matuledesktop.data.database.remote

import org.radlance.matuledesktop.data.database.remote.entity.BrandEntity
import org.radlance.matuledesktop.data.database.remote.entity.CartItemEntity
import org.radlance.matuledesktop.data.database.remote.entity.CategoryEntity
import org.radlance.matuledesktop.data.database.remote.entity.ClaspTypeEntity
import org.radlance.matuledesktop.data.database.remote.entity.ColorEntity
import org.radlance.matuledesktop.data.database.remote.entity.MoistureProtectionTypeEntity
import org.radlance.matuledesktop.data.database.remote.entity.OriginCountryEntity
import org.radlance.matuledesktop.data.database.remote.entity.ProductEntity
import org.radlance.matuledesktop.data.database.remote.entity.ProductSizeEntity
import org.radlance.matuledesktop.data.database.remote.entity.SizeEntity
import org.radlance.matuledesktop.domain.cart.CartItem
import org.radlance.matuledesktop.domain.product.Brand
import org.radlance.matuledesktop.domain.product.Category
import org.radlance.matuledesktop.domain.product.ClaspType
import org.radlance.matuledesktop.domain.product.MoistureProtectionType
import org.radlance.matuledesktop.domain.product.OriginCountry
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.domain.product.ProductColor
import org.radlance.matuledesktop.domain.product.ProductSize
import org.radlance.matuledesktop.domain.product.Size

abstract class RemoteMapper {
    protected fun CategoryEntity.toCategory(): Category {
        return Category(id = id, title = title)
    }

    protected fun ProductEntity.toProduct(
        isFavorite: Boolean
    ): Product {
        return Product(
            id = id,
            title = title,
            price = price,
            description = description,
            imageUrl = imageUrl,
            isFavorite = isFavorite,
            categoryId = categoryId,
            isPopular = isPopular,
            originCountryId = originCountryId,
            sizes = sizes.map { it.toProductSize() },
            brandId = brandId,
            claspTypeId = claspTypeId,
            moistureProtectionTypeId = moistureProtectionTypeId,
            colors = colors.map { it.toColor() },
            modelName = modelName
        )
    }

    protected fun ProductSizeEntity.toProductSize(): ProductSize {
        return ProductSize(sizeId = sizeId, size = size, quantity = quantity)
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

    protected fun MoistureProtectionTypeEntity.toMoistureProtectionType(): MoistureProtectionType {
        return MoistureProtectionType(id = id, name = name)
    }

    protected fun ColorEntity.toColor(): ProductColor {
        return ProductColor(id = id, name = name, red = red, green = green, blue = blue)
    }

    protected fun CartItemEntity.toCartItem(): CartItem {
        return CartItem(
            productId = productId,
            productSize = productSize,
            quantity = quantity
        )
    }
}
