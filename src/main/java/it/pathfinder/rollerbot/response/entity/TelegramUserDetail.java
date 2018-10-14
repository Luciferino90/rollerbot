package it.pathfinder.rollerbot.response.entity;

import it.pathfinder.rollerbot.data.entity.TelegramUser;
import it.pathfinder.rollerbot.response.Info;
import lombok.Data;

import java.io.Serializable;

@Data
public class TelegramUserDetail extends TelegramUser implements Serializable, Info {

    public TelegramUserDetail()
    {
        super();
    }


}
