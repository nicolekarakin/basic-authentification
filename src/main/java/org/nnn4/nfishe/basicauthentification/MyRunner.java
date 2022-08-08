package org.nnn4.nfishe.basicauthentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements ApplicationRunner {

    private String name="";
    private String encoded="";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        tryThat();
        inputString();
    }

    private void inputString(){
        Scanner input = new Scanner(System.in);
        while(!name.equals("exit")){
            System.out.print("Enter your name: ");
            // takes input from the keyboard
           name = input.nextLine();
           encryptName();
        }
        if(name.equals("exit")) input.close();
    }

    private void encryptName(){
        if(!name.equals("exit")&&name.length()>0){
            int hashed=passwordEncoder.hashCode();
            boolean matchOkOld=passwordEncoder.matches(name, encoded);
            String encodedNew=passwordEncoder.encode(name);
            boolean matchOkNew=passwordEncoder.matches(name, encodedNew);

            System.out.println(name+
                    " encoded: "+ encodedNew+
                    " hash: "+ hashed+
                    ", matchOkOld: "+ matchOkOld+
                    ", matchOkNew: "+ matchOkNew);
        }
    }

    private void tryThat(){
        String[]names={"sara","Yasu"};
        int[]hashes0={1439484636,1403603697};
        int[]hashes={10,12};
        String[]salts={BCrypt.gensalt(hashes[0]),BCrypt.gensalt(hashes[1])};
        String decodeSara1=BCrypt.hashpw(names[0].toString(), salts[0]);
        String decodeSara2=BCrypt.hashpw(names[0].toString(), salts[1]);
        String decodeYasu1=BCrypt.hashpw(names[1].toString(), salts[0]);
        String decodeYasu2=BCrypt.hashpw(names[1].toString(), salts[1]);
        System.out.println("decodeSara1: "+decodeSara1+", decodeSara2: "+decodeSara2);
        System.out.println("decodeYasu1: "+decodeYasu1+", decodeYasu2: "+decodeYasu2);

//        decodeSara1: $2a$10$EBNnvAvYMf2MvGc8LYCs6eZvg.T6FBts4J72oLonlpkW8JdZtpm52, decodeSara2: $2a$12$hY0IhgzbRhUDsv7PWdy6yOjZRhURD.C1uSnQ4zRc4FGNhV.IA4AHC
//        decodeYasu1: $2a$10$EBNnvAvYMf2MvGc8LYCs6e5T9YCTzlExSFoqBD6HEeID5z9tGOwui, decodeYasu2: $2a$12$hY0IhgzbRhUDsv7PWdy6yOHXOy7mOFpoTIT.PcjoKkKhASTNKTmcG
    }
}

//sara encoded: $2a$10$dj4pxk6iHCH/kJjkQvLV4u4W73XNQqS7N6McTDLOlmkWwYwed27tm hash: 1439484636, matchOkOld: false, matchOkNew: true
//sara encoded: $2a$10$NlfPnG0Yojd2F3TiaAOreOIQWZSyGQLfodiVe.Za/Fk37NIP/tG8S hash: 1439484636, matchOkOld: false, matchOkNew: true
//Yasu encoded: $2a$10$AkB5KDTd0QJMxRdkkeUOHuM5yl2rIvBaIaFO9NpwmaYB8xJusQaMG hash: 1439484636, matchOkOld: false, matchOkNew: true
//new app start============================================================
//sara encoded: $2a$10$5M2jFb3ggBOQY4YZayo64eA2fJqIwwmJ.t00.0dd8Xr3.g79akQYC hash: 1403603697, matchOkOld: false, matchOkNew: true
//sara encoded: $2a$10$gREXEedy5hvj.OZigicAn.fUAuYm4w1k2y6imTxSMbyH3.kh5.5Ly hash: 1403603697, matchOkOld: false, matchOkNew: true
//Yasu encoded: $2a$10$6utwyO9rBoKCbziqhJL2muChmbn4TkYTh5CWwdBz/y1G2E8mOf4Sm hash: 1403603697, matchOkOld: false, matchOkNew: true