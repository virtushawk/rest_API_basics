package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class V1_2__updateGiftCertificateDuration extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement update = context.getConnection().createStatement()) {
            update.executeUpdate("UPDATE gift_certificate SET duration = duration + 1");
        }
    }
}
