package org.cubewhy.lunarcn.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.account.OfflineAccount;
import org.cubewhy.lunarcn.utils.AccountUtils;

import java.io.*;

public class AccountConfigFile {
    public static String configFilePath = Client.configDir + "/accounts.json";
    private static AccountConfigFile instance = null;
    private static JsonObject config;

    public static AccountConfigFile getInstance() {
        if (instance == null) {
            instance = new AccountConfigFile();
        }
        return instance;
    }

    public void addAccount(IAccount account) {
        JsonElement json = account.toJson();
        if (account instanceof OfflineAccount) {
            for (String key :
                    config.keySet()) {
                if (config.get(key).getAsJsonObject().get("username").getAsString().equals(account.getUserName())) {
                    return;
                }
            }
        }
        config.add(account.getUuid(), json);
        save();
    }

    public void setCurrentAccount(IAccount account) {
        String uuid = account.getUuid();
        config.addProperty("current", uuid);
        save();
    }

    public void removeAccount(IAccount account) {
        config.remove(account.getUuid());
        save();
    }

    public IAccount getCurrentAccount() {
        if (!config.has("current")) {
            return null;
        }
        String current = config.get("current").getAsString();
        if (!config.has(current)) {
            return null;
        }

        return AccountUtils.fromJson(config.get(current).getAsJsonObject());
    }

    public void save() {
        try {
            File configFile = new File(configFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
            bufferedWriter.write(config.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        Gson gson = new Gson();
        File configFile = new File(configFilePath);
        BufferedReader bufferedReader;
        boolean successful = false;

        while (!successful) {
            try {
                bufferedReader = new BufferedReader(new FileReader(configFile));
                config = gson.fromJson(bufferedReader, JsonObject.class);
                if (config == null) {
                    config = new JsonObject();
                }
                successful = true;
            } catch (FileNotFoundException e) {

                try {
                    if (!configFile.getParentFile().exists()) {
                        configFile.getParentFile().mkdirs();
                    }
                    configFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    public JsonObject getConfig() {
        return config;
    }
}
