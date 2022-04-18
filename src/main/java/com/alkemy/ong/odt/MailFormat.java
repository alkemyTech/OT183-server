package com.alkemy.ong.odt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailFormat {
    
    private String mailSender;
    private String mailReciver;
    private String content;
}
