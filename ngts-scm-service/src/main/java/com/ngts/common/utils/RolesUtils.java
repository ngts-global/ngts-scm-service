package com.ngts.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ngts.common.func.pojo.Functions;
import com.ngts.common.func.pojo.RoleFunctionMapping;
import com.ngts.common.func.pojo.RolesGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RolesUtils {

    //@Value("classpath:functionslist.json")
    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    public static String ADMIN = "ADMIN";
    public static String STAFFS = "STAFFS";
    public static String STUDENTS = "STUDENTS";

    /*private static  String[] studentsAccessibleUris = new String[]{ "/scm/api/student/**" };
    private static  String[] staffsAccessibleUris = new String[]{ "/scm/api/teacher/**"  };
    private static  String[] adminAccessibleUris = new String[]{ "/scm/api/student/**" };



    public static HashMap<String , String[]> apiList = new HashMap<>(){
        {
            put(STUDENTS, studentsAccessibleUris);
            put(STAFFS, merge(staffsAccessibleUris, studentsAccessibleUris));
            put(ADMIN, merge(adminAccessibleUris,  merge(staffsAccessibleUris, studentsAccessibleUris)));
        }

        private String[] merge(String[] adminAccessibleUris, String[] staffsAccessibleUris) {
            String[] both = Stream.of(adminAccessibleUris, staffsAccessibleUris).flatMap(Stream::of)
                    .toArray(String[]::new);
            return both;
        }
    };*/
    public RoleFunctionMapping getRoleFunctionMapping() throws IOException {
        //Resource resource = ctx.getResource("classpath:functionslist.json");

        ClassLoader classLoader = getClass().getClassLoader();
        InputStreamReader in = null;
        in = new InputStreamReader(classLoader.getResourceAsStream("functionslist.json"));

        RoleFunctionMapping roleFunctionMapping = objectMapper.readValue(in, RoleFunctionMapping.class);
        return roleFunctionMapping;
    }
    /**
     *
     * @param role
     * @return
     */
    public String[] getApiUrisForSecurity(String role)  {
        try {

            Map<String, RolesGroup> rolesGroupMap = getRoleFunctionMapping().getRolesGroupList().stream()
                    .collect(Collectors.toMap(RolesGroup::getRoleName, Function.identity()));
            System.out.println(rolesGroupMap);
            List<String> functionsList = rolesGroupMap.get(role).getFunctionsList().stream().map(Functions::getFuncValue).collect(Collectors.toList());
            String[] functions = Arrays.copyOf(
                    functionsList.toArray(), functionsList.size(), String[].class);
            return functions;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param role
     * @return
     */
    public List<Functions> getFunctionsList(String role) {
        try {
            Map<String, RolesGroup> rolesGroupMap = getRoleFunctionMapping().getRolesGroupList().stream()
                    .collect(Collectors.toMap(RolesGroup::getRoleName, Function.identity()));
            return rolesGroupMap.get(role).getFunctionsList();
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

    public static void main(String[] args) {
        //
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            RoleFunctionMapping roleFunctionMapping = new RoleFunctionMapping();
            roleFunctionMapping = objectMapper.readValue(Paths.get("/Users/Work/Downloads/Techy/Bitbucket-RPGM/Projects/NGTS-Projects/SCM/ngts-scm/src/main/resources/functionslist.json").toFile(), RoleFunctionMapping.class);

            Map<String, RolesGroup> rolesGroupMap = roleFunctionMapping.getRolesGroupList().stream()
                    .collect(Collectors.toMap(RolesGroup::getRoleName, Function.identity()));
            System.out.println(rolesGroupMap);

           List<String> functionsList = rolesGroupMap.get("ADMIN").getFunctionsList().stream().map(Functions::getFuncValue).collect(Collectors.toList());
            List<Functions> functionsList1 =  rolesGroupMap.get("ADMIN").getFunctionsList();
           functionsList1.forEach(functions -> {
               System.out.println( functions.getFuncKey());
           });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
