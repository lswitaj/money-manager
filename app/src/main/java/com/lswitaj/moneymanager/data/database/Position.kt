package com.lswitaj.moneymanager.data.database

//TODO(to create a user object)
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lswitaj.moneymanager.utils.countProportion
import com.lswitaj.moneymanager.utils.formatPrice
import com.lswitaj.moneymanager.utils.formatDouble
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

//TODO(to add a type - stock, crypto, etf, cash)
//TODO(add the transaction timestamp and updateTime instead of the positionId that can differ between the app and backend)
@Parcelize
@Entity(tableName = "positions")
data class Position(
    @ColumnInfo(name = "positionName")
    var positionName: String,
    //TODO(to add currency)
    //var currency: String = "USD",

    //TODO(add a timestamp so that the app knows if the position price should/not be updated)
    //TODO(to rethink using BigDecimal instead of the Double)
    var buyPrice: Double = 0.0,
    var quantity: Double = 0.0,
    var lastClosePrice: Double = 0.0
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true) var positionId: Long = 0L

    constructor(position: Position) : this(
        position.positionName,
        position.buyPrice,
        position.quantity,
        position.lastClosePrice
    )

    //TODO(hardcoded currency to be removed)
    fun displayBuyPrice() : String = formatPrice(buyPrice, "USD")
    fun displayQuantity() : String = formatDouble(quantity)
    fun displayCurrentPrice() : String = formatPrice(lastClosePrice, "USD")
    fun displayProfitRation() : String = countProportion(buyPrice, lastClosePrice)
}