package dto.generic.entity;

import dto.generic.GenericDTO;
import it.pathfinder.rollerbot.data.entity.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class TelegramUserDetail extends GenericDTO implements Serializable {

    private TelegramUser telegramUser;

    private String className;

    @Override
    public String toString() {
        return String.format("%s: %s", telegramUser.getTgUsername(), telegramUser.getDefaultPathfinderPg().toString());
    }

}
