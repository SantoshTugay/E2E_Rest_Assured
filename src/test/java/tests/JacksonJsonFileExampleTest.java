package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.employee.Employee;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JacksonJsonFileExampleTest {

//    We use Jackson’s ObjectMapper to deserialize JSON files into POJOs for strict validation,
//    Maps for flexible structures, and TypeReference for generic collections. Serialization
//    is handled using writeValueAsString to convert Java objects back into JSON

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper(); // 🔹 Core Jackson class

        File jsonFile =
                new File("src/test/resources/employee.json");

        // ==========================================
        // 1️⃣ DESERIALIZATION → POJO (File → Object)
        // ==========================================
        Employee emp =
                mapper.readValue(jsonFile, Employee.class);

        System.out.println("POJO Deserialization:");
        System.out.println(emp.name + " | " + emp.address.city);
        System.out.println("Projects count: " + emp.projects.size());

        // ==========================================
        // 2️⃣ DESERIALIZATION → MAP (Flexible)
        // ==========================================
        Map<String, Object> map =
                mapper.readValue(jsonFile, Map.class);

        System.out.println("\nMap Deserialization:");
        System.out.println(map.get("name"));
        System.out.println(
                ((Map<?, ?>) map.get("address")).get("city")
        );

        // ==========================================
        // 3️⃣ TypeReference (ARRAY / GENERIC)
        // ==========================================
        String jsonArrayFilePath =
                "src/test/resources/employees.json";

        File arrayFile = new File(jsonArrayFilePath);

        List<Employee> employees =
                mapper.readValue(
                        arrayFile,
                        new TypeReference<List<Employee>>() {}
                );

        System.out.println("\nTypeReference (List<Employee>):");
        System.out.println(employees.get(0).name);

        // ==========================================
        // 4️⃣ SERIALIZATION (Object → JSON String)
        // ==========================================
        String serializedJson =
                mapper.writeValueAsString(emp);

        System.out.println("\nSerialized JSON:");
        System.out.println(serializedJson);
    }
}

