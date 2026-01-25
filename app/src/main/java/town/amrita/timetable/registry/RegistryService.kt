package town.amrita.timetable.registry

import kotlinx.serialization.json.Json
import town.amrita.timetable.models.Timetable
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RegistryService {
  interface RegistryServiceImpl {
    @GET("index.json")
    suspend fun getRegistry(): Registry

    @GET("files/{year}/{section}/{sem}.json")
    suspend fun getTimetable(
      @Path("year") year: String,
      @Path("section") section: String,
      @Path("sem") semester: String
    ): Timetable
  }

  private val cache = mutableMapOf<TimetableSpec, Timetable>()

  val impl: RegistryServiceImpl by lazy {
    Retrofit.Builder()
      .baseUrl("https://timetable-registry.amrita.town/v2/")
      .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
      .build()
      .create(RegistryServiceImpl::class.java)
  }

  suspend fun getRegistry(): Registry = impl.getRegistry()
  
  suspend fun getTimetable(spec: TimetableSpec): Timetable {
    return cache.getOrPut(spec) {
      impl.getTimetable(spec.year, spec.section, spec.semester)
    }
  }

  companion object {
    val instance = RegistryService()
  }
}