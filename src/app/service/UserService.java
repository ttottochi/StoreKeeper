package app.service;

import persistence.dao.MyUserDAO;
import persistence.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

public class UserService
{
    private final int ID_AND_PW_MIN_LENGTH = 3;
    private final MyUserDAO myUserDAO;

    public UserService()
    {
        myUserDAO = new MyUserDAO();
    }

    public List<UserDTO> findAll()
    {
        List<UserDTO> all = myUserDAO.selectAll();
        return all;
    }

    public UserDTO findUser(String user_id)
    {
        return myUserDAO.selectUserById(user_id);
    }

    public boolean idCheck(String userid)
    {
        List<UserDTO> userDTOS = myUserDAO.selectAllUserid();
        for(UserDTO userDTO : userDTOS)
        {
            if(userid.equals(userDTO.getUser_id()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean pwCheck(String userid, String userpw) {
        List<UserDTO> userDTOS = myUserDAO.selectAllUserid();
        for(UserDTO userDTO : userDTOS)
            if(userid.equals(userDTO.getUser_id())) {
                UserDTO user_pwDTO=myUserDAO.selectUserpw(userDTO);
                if (userpw.equals(user_pwDTO.getUser_pw()))
                    return true;
            }
        return false;
    }

    public void userAdd()
    {
        Scanner sc = new Scanner(System.in);
        UserDTO addUserDTO = new UserDTO();

        String user_id = inputUser_id(sc, myUserDAO);
        String user_pw = inputUser_pw(sc);
        String user_name = inputUser_name(sc);
        String user_address = inputUser_address(sc);
        String user_phone = inputUser_phone(sc);
        int user_category = inputUser_category(sc);


        addUserDTO.setUser_id(user_id);
        addUserDTO.setUser_pw(user_pw);
        addUserDTO.setUser_name(user_name);
        addUserDTO.setUser_address(user_address);
        addUserDTO.setUser_phone(user_phone);
        addUserDTO.setUser_category(user_category);

        myUserDAO.userAdd(addUserDTO);
    }

    private String inputUser_id(Scanner sc, MyUserDAO myUserDAO) {
        String input = null;
        boolean result = true;
        List<UserDTO> userDTOS = myUserDAO.selectAllUserid();

        while(true)
        {
            result = true;
            System.out.print("???????????? ??????????????????. : ");
            input = sc.nextLine();

            if(input.length() < ID_AND_PW_MIN_LENGTH)
            {
                System.out.println("???????????? ??? ?????? ??????????????????. (?????? ??? ??????)");
                result = false;
            }
            else
            {
                if(idCheck(input))
                {
                    System.out.println("???????????? ??? ?????? ??????????????????. (?????? ?????????)");
                    result = false;
                }
            }
            if(result)
                return input;
        }
    }

    private String inputUser_pw(Scanner sc)
    {
        String input = null;

        while(true)
        {
            System.out.print("??????????????? ???????????????. : ");
            input = sc.nextLine();

            if(input.length() >= ID_AND_PW_MIN_LENGTH)
                return input;
            else
                System.out.println("?????? ????????? ?????? ????????????(?????? ??? ??????). ");

        }
    }
    private String inputUser_name(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("????????? ?????????????????? : ");
            input = sc.nextLine();

            if(input.length() > 0)
            {
                return input;
            }
            else
                System.out.println("?????? ????????? ?????? ????????????(?????? ??? ??????). ");
        }

    }

    private String inputUser_address(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("????????? ?????????????????? : ");
            input = sc.nextLine();

            if(input.length() > 0)
            {
                return input;
            }
            else
                System.out.println("?????? ????????? ?????? ????????????(?????? ??? ??????). ");
        }
    }

    private String inputUser_phone(Scanner sc)
    {
        String input = null;
        while(true)
        {
            System.out.print("??????????????? ?????????????????? : ");
            input = sc.nextLine();

            if (isdigit(input))
                return input;
            else
                System.out.println("?????? ????????? ?????? ????????????. ");
        }

    }

    private int inputUser_category(Scanner sc)
    {
        int input;
        while(true)
        {
            System.out.print("?????? ????????? ??????????????????(0:?????????, 1:??????, 2:??????) : ");
            input = sc.nextInt();

            if (0 <= input && input <= 2)
                return input;
            else
                System.out.println("?????? ????????? ?????? ????????????. ");
        }

    }


    private boolean isdigit(String input)
    {
        char tmp;

        for(int i = 0; i<input.length(); i++)
        {
            tmp = input.charAt(i);
            if(!('0' <= tmp && tmp <= '9'))
                return false;
        }

        return true;
    }

    private boolean isTime(String input)
    {
        String[] temp = input.split(":");
        for(int i = 0; i < temp.length; i++)
        {
            if(!(isdigit(temp[i]) && temp[i].length() == 2))
                return false;
        }
        if(0 <= Integer.parseInt(temp[0]) && Integer.parseInt(temp[0]) <= 24)
            if(0 <= Integer.parseInt(temp[1]) && Integer.parseInt(temp[1]) < 60)
                return true;

        return false;
    }

    private boolean compareTime(String open, String close)
    {
        String[] open_temp = open.split(":");
        String[] close_temp = close.split(":");

        if(Integer.parseInt(open_temp[0]) < Integer.parseInt(close_temp[0]))
            return true;
        else if(Integer.parseInt(open_temp[0]) == Integer.parseInt(close_temp[0]))
        {
            if(Integer.parseInt(open_temp[1]) <= Integer.parseInt(close_temp[1]))
                return true;
        }

        return false;
    }


}

