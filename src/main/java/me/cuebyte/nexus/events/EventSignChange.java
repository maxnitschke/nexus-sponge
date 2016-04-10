package main.java.me.cuebyte.nexus.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Text;


public class EventSignChange {

    @Listener
    public void onSignChange(ChangeSignEvent event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;
    	
    	Player player = optional.get();

    	Sign sign = event.getTargetTile();
    	
		SignData data = event.getText();
		List<Text> lines = data.get(Keys.SIGN_LINES).get();
		
    	if(PermissionsUtils.has(player, "nexus.signs.color")) {
    	
    		List<Text> n = new ArrayList<Text>();
    		
    		for(int i = 0; i <= 3; i++) {
    			if( lines.size() < i + 1) continue;
    			Text line =  lines.get(i);
    			String plain = line.toPlain();
    			line = TextUtils.color(plain);
    			n.add(line);
    		}
    		
    		data.set(Keys.SIGN_LINES, n);
    		sign.offer(data);
    		
    	}
    	
    }
	
}
