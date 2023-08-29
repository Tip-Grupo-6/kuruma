import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Car {
    @Id
    var id: Long = 0
    var name: String = ""
    var brand: String = ""
    var model: String = ""
    var year: Int = 0
    var color: String = ""
    var image: String = ""
    var isDeleted: Boolean = false
}