package lab6.common;

import lab6.common.dto.CoordinatesDto;
import lab6.common.dto.PersonDto;
import lab6.common.dto.WorkerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transformer {
    private static final Logger logger
            = LoggerFactory.getLogger(Transformer.class);
    public static String Encrypt(String password, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return generatedPassword;
    }


    public static WorkerDto WorkerToWorkerDto(Worker bum) {
        WorkerDto man = new WorkerDto();
        man.setName(bum.getName());
        man.setCoordinates(CoordinatesToCoordinatesDto(bum.getCoordinates()));
        man.setCreationDate(bum.getCreationDate());
        man.setSalary(bum.getSalary());
        man.setId(bum.getId());
        man.setStartDate(bum.getStartDate());
        man.setEndDate(bum.getEndDate());
        man.setPerson(PersonToPersonDto(bum.getPerson()));
        man.setPosition(bum.getPosition());
        man.setUser(bum.getUser());
        return man;
    }

    public byte[] Serialize(Serializable obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object DeSerialize(byte[] arr) {
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        try (ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PersonDto PersonToPersonDto(Person person) {
        PersonDto pers = new PersonDto();
        pers.setBirthday(person.getBirthday());
        pers.setHeight(person.getHeight());
        pers.setWeight(person.getWeight());
        return pers;
    }


    public static CoordinatesDto CoordinatesToCoordinatesDto(Coordinates coordinates) {
        CoordinatesDto coords = new CoordinatesDto();
        coords.setX(coordinates.getX());
        coords.setY(coordinates.getY());
        return coords;
    }


}
