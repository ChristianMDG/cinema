# ReservationsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createReservation**](ReservationsApi.md#createReservation) | **POST** /reservations | Créer une réservation |
| [**getAllReservations**](ReservationsApi.md#getAllReservations) | **GET** /reservations | Lister toutes les réservations |
| [**getReservationById**](ReservationsApi.md#getReservationById) | **GET** /reservations/{id} | Récupérer une réservation par son id |
| [**updateReservation**](ReservationsApi.md#updateReservation) | **PUT** /reservations/{id} | Modifier une réservation |


<a id="createReservation"></a>
# **createReservation**
> Reservation createReservation(reservationRequest)

Créer une réservation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReservationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ReservationsApi apiInstance = new ReservationsApi(defaultClient);
    ReservationRequest reservationRequest = new ReservationRequest(); // ReservationRequest | 
    try {
      Reservation result = apiInstance.createReservation(reservationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReservationsApi#createReservation");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **reservationRequest** | [**ReservationRequest**](ReservationRequest.md)|  | |

### Return type

[**Reservation**](Reservation.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Réservation créée |  -  |
| **400** | Données invalides |  -  |
| **401** | Non authentifié |  -  |

<a id="getAllReservations"></a>
# **getAllReservations**
> List&lt;Reservation&gt; getAllReservations()

Lister toutes les réservations

- **CLIENT** : 403 Forbidden - **EMPLOYEE** : 200 OK - **MANAGER** : 200 OK 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReservationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ReservationsApi apiInstance = new ReservationsApi(defaultClient);
    try {
      List<Reservation> result = apiInstance.getAllReservations();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReservationsApi#getAllReservations");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Reservation&gt;**](Reservation.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des réservations |  -  |
| **401** | Non authentifié |  -  |
| **403** | Accès refusé (rôle CLIENT) |  -  |

<a id="getReservationById"></a>
# **getReservationById**
> Reservation getReservationById(id)

Récupérer une réservation par son id

- **CLIENT propriétaire** de la réservation : 200 OK - **CLIENT** non-propriétaire : 403 Forbidden - **EMPLOYEE** : 200 OK - **MANAGER** : 200 OK 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReservationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ReservationsApi apiInstance = new ReservationsApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Identifiant UUID de la réservation
    try {
      Reservation result = apiInstance.getReservationById(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReservationsApi#getReservationById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**| Identifiant UUID de la réservation | |

### Return type

[**Reservation**](Reservation.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Réservation trouvée |  -  |
| **401** | Non authentifié |  -  |
| **403** | Accès refusé (CLIENT non-propriétaire) |  -  |
| **404** | Réservation introuvable |  -  |

<a id="updateReservation"></a>
# **updateReservation**
> Reservation updateReservation(id, reservationRequest)

Modifier une réservation

- **CLIENT** : 403 Forbidden - **EMPLOYEE** : 200 OK - **MANAGER** : 200 OK 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReservationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ReservationsApi apiInstance = new ReservationsApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Identifiant UUID de la réservation
    ReservationRequest reservationRequest = new ReservationRequest(); // ReservationRequest | 
    try {
      Reservation result = apiInstance.updateReservation(id, reservationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReservationsApi#updateReservation");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**| Identifiant UUID de la réservation | |
| **reservationRequest** | [**ReservationRequest**](ReservationRequest.md)|  | |

### Return type

[**Reservation**](Reservation.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Réservation mise à jour |  -  |
| **401** | Non authentifié |  -  |
| **403** | Accès refusé (rôle CLIENT) |  -  |
| **404** | Réservation introuvable |  -  |

