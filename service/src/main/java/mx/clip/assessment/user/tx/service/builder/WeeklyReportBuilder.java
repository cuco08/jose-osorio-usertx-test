package mx.clip.assessment.user.tx.service.builder;

import mx.clip.assessment.user.tx.api.model.WeeklyReport;

public class WeeklyReportBuilder {

    private WeeklyReport weeklyReport;

    public WeeklyReportBuilder() {
        weeklyReport = new WeeklyReport();
    }

    public WeeklyReport build() {
        return weeklyReport;
    }

    public WeeklyReportBuilder withUserId(String userId) {
        weeklyReport.userId(userId);
        return this;
    }

    public WeeklyReportBuilder withStartWeek(String startWeek) {
        weeklyReport.startWeek(startWeek);
        return this;
    }

    public WeeklyReportBuilder withFinishWeek(String finishWeek) {
        weeklyReport.finishWeek(finishWeek);
        return this;
    }

    public WeeklyReportBuilder withQuantity(int quantity) {
        weeklyReport.quantity(quantity);
        return this;
    }

    public WeeklyReportBuilder withAmount(double amount) {
        weeklyReport.amount(amount);
        return this;
    }

    public WeeklyReportBuilder withTotalAmount(double totalAmount) {
        weeklyReport.totalAmount(totalAmount);
        return this;
    }
}
