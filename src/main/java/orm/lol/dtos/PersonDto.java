//package orm.lol.dtos;
//
//import orm.lol.entites.person.Person;
//
//import java.util.UUID;
//
//public record PersonDto(String firstName, String lastName, UUID businessEntityGuid) {
//    public static PersonDto toDto(Person p) {
//        return new PersonDto(
//                p.getFirstName(),
//                p.getLastName(),
//                p.getBusinessEntity().getRowguid()
//        );
//    }
//}
//
