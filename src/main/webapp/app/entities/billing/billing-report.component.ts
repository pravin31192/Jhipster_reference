import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BillingService } from './billing.service';

@Component({
    selector: 'jhi-billing-report',
    templateUrl: './billing-report.component.html'
})
export class BillingReportComponent implements OnInit {
    initialBillingReport: any[];
    monthArray: any[];
    quartely = 0;
    quaterOneTotal = 0;
    quaterTwoTotal = 0;
    quaterThreeTotal = 0;
    quaterFourTotal = 0;
    grandTotal = 0;
    quaterOneArray: any[] = [];
    quaterTwoArray: any[] = [];
    quaterThreeArray: any[] = [];
    quaterFourArray: any[] = [];
    projectBasedTotal: any[] = [];
    constructor(protected billingService: BillingService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.loadInitialBillingReport();
        this.initiateMonthArray();
    }

    previousState() {
        window.history.back();
    }

    initiateMonthArray() {
        this.monthArray = [
            { 1 : 'Jan' },
            { 2 : 'Feb' },
            { 3 : 'Mar' },
            { 4 : 'Apr' },
            { 5 : 'May' },
            { 6 : 'Jun' },
            { 7 : 'Jul' },
            { 8 : 'Aug' },
            { 9 : 'Sep' },
            { 10 : 'Oct' },
            { 11 : 'Nov' },
            { 12 : 'Dec' },
        ];
    }

    loadInitialBillingReport() {
        this.billingService.getInitialBillingReport().subscribe(
            result => {
                console.log(result.body);
                this.initialBillingReport = result.body;
            },
            error => {
                console.log('Error has occured');
            }
        );
    }

    getCheck(fromMonth: number, toMonth: number, currentMonth: number, i: number) {
        if (fromMonth <= currentMonth && toMonth >= currentMonth) {

            // if (currentMonth <= 3) {
            //     console.log(i);
            //     this.quaterOneTotal = this.initialBillingReport[i].total + this.quaterOneTotal;
            //     this.quaterOneArray[i] = this.quaterOneTotal;
            //     console.log(this.quaterOneArray);
            //     console.log(this.quaterOneTotal);
            // }
            
            
            return true;
        } else {
            return false;
        }
    }

    sumQ1(monthlyValue, projectSlNo, month) {
        if (month <= 3) {
            
            this.quaterOneTotal = +this.quaterOneTotal + +monthlyValue;
            this.quaterOneArray[projectSlNo] = this.quaterOneTotal;
        }
        console.log(projectSlNo);
        console.log(month);
        console.log(this.quaterOneArray);
        return 0;
    }
}
