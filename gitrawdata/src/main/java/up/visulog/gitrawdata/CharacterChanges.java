package up.visulog.gitrawdata;

import java.util.Date;

public class CharacterChanges extends ChangesDescription{
    public final int addedCharacters;
    public final int removedCharcaters;

    public CharacterChanges(String id, String author, Date date, String description, int added, int removed) { // simplement le constructeur
        super(id, author, date, description);
        this.addedCharacters = added;
        this.removedCharcaters = removed;
    }
}
