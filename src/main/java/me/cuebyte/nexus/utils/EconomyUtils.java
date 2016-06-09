package main.java.me.cuebyte.nexus.utils;

import java.math.BigDecimal;
import java.util.Optional;

import main.java.me.cuebyte.nexus.Nexus;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

public class EconomyUtils {
	
	public static EconomyService economy;
	
	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
	        if (event.getService().equals(EconomyService.class)) {
	                economy = (EconomyService) event.getNewProviderRegistration().getProvider();
	        }
	}
	
	public static double get(Player player) {
		
		Optional<UniqueAccount> account = economy.getOrCreateAccount(player.getUniqueId());
		
		if (!account.isPresent()) return 0;
		
		UniqueAccount a = account.get();
		BigDecimal balance = a.getBalance(economy.getDefaultCurrency());
		return balance.doubleValue();
		
	}
	
	public static void add(Player player, double amount) {
		
		Optional<UniqueAccount> account = economy.getOrCreateAccount(player.getUniqueId());
		
		if (!account.isPresent()) return;
		
		BigDecimal bd = BigDecimal.valueOf(amount);
		
		UniqueAccount a = account.get();
		a.deposit(economy.getDefaultCurrency(), bd, Cause.source(Nexus.getInstance()).build());
		
	}
	
	public static void remove(Player player, double amount) {
		
		Optional<UniqueAccount> account = economy.getOrCreateAccount(player.getUniqueId());
		
		if (!account.isPresent()) return;
		
		BigDecimal bd = BigDecimal.valueOf(amount);
		
		UniqueAccount a = account.get();
		a.withdraw(economy.getDefaultCurrency(), bd, Cause.source(Nexus.getInstance()).build());
		
	}

}
