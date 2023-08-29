class Car {
    var name: String = ""
    var brand: String = ""
    var model: String = ""
    var year: Int = 0
    var color: String = ""
    var image: String = ""
    var isDeleted: Boolean = false
}

// Getters and setters

// instance method to get name
fun Car.getName(): String {
    return this.name
}

fun Car.setName(name: String) {
    this.name = name
}

fun Car.getBrand(): String {
    return this.brand
}

fun Car.setBrand(brand: String) {
    this.brand = brand
}

fun Car.getModel(): String {
    return this.model
}

fun Car.setModel(model: String) {
    this.model = model
}

fun Car.getYear(): Int {
    return this.year
}

fun Car.setYear(year: Int) {
    this.year = year
}

fun Car.getColor(): String {
    return this.color
}

fun Car.setColor(color: String) {
    this.color = color
}

fun Car.getImage(): String {
    return this.image
}

fun Car.setImage(image: String) {
    this.image = image
}

fun Car.getIsDeleted(): Boolean {
    return this.isDeleted
}

fun Car.setIsDeleted(isDeleted: Boolean) {
    this.isDeleted = isDeleted
}

