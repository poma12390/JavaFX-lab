package lab6.common;

import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CoordinatesDto;
import lab6.common.dto.PersonDto;
import lab6.common.dto.WorkerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transformer {
    private static final Logger logger
            = LoggerFactory.getLogger(Transformer.class);

    public static WorkerDto WorkerToWorkerDto(Worker bum){
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
        return man;
    }
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

    public static byte[] serialize(Serializable obj){
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object   DeSerialize(byte[] arr ){
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        try(ObjectInputStream in = new ObjectInputStream(bis)) {
                return in.readObject();

        } catch (IOException | ClassNotFoundException  e) {
            throw new RuntimeException(e);
        }
    }

    public CommandRequestDto<? extends Serializable> Testdes(byte[] arr){
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        try(ObjectInput in = new ObjectInputStream(bis)) {
            return (CommandRequestDto<? extends Serializable>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }public static Worker WorkerDtoToWorker(WorkerDto man){
        Worker bum = new Worker(man.getUser());
        bum.setName(man.getName());
        bum.setCoordinates(CoordinatesDtoToCoordinates(man.getCoordinates()));
        bum.setCreationDate(man.getCreationDate());
        bum.setSalary(man.getSalary());
        bum.setId(man.getId());
        bum.setStartDate(man.getStartDate());
        bum.setEndDate(man.getEndDate());
        bum.setPerson(PersonDtoToPerson(man.getPerson()));
        bum.setPosition(man.getPosition());

        return bum;
    }
    public static PersonDto PersonToPersonDto(Person person){
        PersonDto pers = new PersonDto();
        pers.setBirthday(person.getBirthday());
        pers.setHeight(person.getHeight());
        pers.setWeight(person.getWeight());
        return pers;
    }
    public static Person PersonDtoToPerson(PersonDto pers){
        Person person = new Person();
        person.setBirthday(pers.getBirthday());
        person.setHeight(pers.getHeight());
        person.setWeight(pers.getWeight());
        return person;
    }
    public static Coordinates CoordinatesDtoToCoordinates(CoordinatesDto coordto){
        Coordinates coordinates = new Coordinates();
        coordinates.setXY(coordto.getX(), coordto.getY());
        return coordinates;
    }
    public static CoordinatesDto CoordinatesToCoordinatesDto(Coordinates coordinates){
        CoordinatesDto coords = new CoordinatesDto();
        coords.setX(coordinates.getX());
        coords.setY(coordinates.getY());
        return coords;
    }
    public static Worker WorkerToWorker(Worker bum, Worker bum1){
        bum.setCoordinates(bum1.getCoordinates());
        bum.setPerson(bum1.getPerson());
        bum.setEndDate(bum1.getEndDate());
        bum.setName(bum1.getName());
        bum.setStartDate(bum1.getStartDate());
        bum.setPosition(bum1.getPosition());
        bum.setStartDate(bum1.getStartDate());
        return bum;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static long[] DateListToLongArray(List<Date> list){
        List<Long> lst = new ArrayList<>();
        long[] arr = new long[list.size()];
        for (Date i : list){
            lst.add(i.getTime());

        }
        for(int i = 0; i < list.size(); i++) arr[i] = lst.get(i);
        Transformer.longArrayToDateList(arr);
        return arr;

    }

    public static List<Date> longArrayToDateList(long[] arr){
        List<Date> lst= new ArrayList<>();
        for (long l : arr) {
            lst.add(new Date(l));
        }
        return lst;
    }


}
