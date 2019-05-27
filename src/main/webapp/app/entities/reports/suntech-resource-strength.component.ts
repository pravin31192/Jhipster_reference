import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import 'chart.js';
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';

import { LoginModalService, AccountService, Account } from 'app/core';
import { BaseChartDirective } from 'ng2-charts';
import { FormBuilder, FormGroup, Validators, FormControl, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProjectResourceAssignService } from 'app/entities/project-resource-assign';
import { EmployeeSalaryService } from 'app/entities/employee-salary';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

@Component({
    selector: 'jhi-suntech-resource-strength.component',
    templateUrl: './suntech-resource-strength.component.html',
    styleUrls: ['suntech-resource-strength.scss']
})
export class SunTechResourceStrengthComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    supportArray: any[];
    engineeringArray: any[];
    employeesInDepartment: any[];
    resourcesOnClients: any[];
    projectsInPrms: any[];
    clientsInHRM: any[];
    data: any;
    imgWidth: any;
    pageHeight: any;
    imgHeight: any;
    heightLeft: any;
    position: any;
    // Doughnut
    public doughnutChartLabels: string[] = [
        'Acoounts',
        'Accounts & Finance',
        'Administration',
        'Domestic Staffing',
        'Facility',
        'Finance',
        'Head Office',
        'HR',
        'HR & Marketing Support',
        'HR Team',
        'IT Support',
        'Sales & Marketing',
        'Sales & Marketing - Offshore',
        'Sun Technologies'
    ];
    public doughnutChartData: number[] = [4, 3, 4, 4, 6, 1, 0, 0, 1, 14, 13, 10, 12, 0];
    public doughnutChartType = 'doughnut';

    public pieChartLabels: string[] = [
        'Application Development Services',
        'Gaming',
        'Off-Shore Team',
        'QA Automation',
        'QA Manual',
        'US Staffing'
    ];

    resourcesBasedOnRole: any[];
    resourcesBasedOnEngRole: any[];
    demo: any[] = [];
    demoEng: any[] = [];
    rolesArray: any[];
    projectResourceAssign: IProjectResourceAssign;

    // public SupportTotal = 0;
    // public EngTotal = 0;
    // public supportLength: any[];
    // public EngineeringLength: any[];
    id: any;
    emp: any;
    newArray: any[] = [];
    newEngArray: any[] = [];
    // test chart

    public pieChartData: number[] = [36, 75, 101, 23, 21, 34];
    public pieChartType = 'doughnut';

    projectResourceAssignForm: FormGroup;

    // content: any;
    // public pieChartData: number[] =  [];
    // public pieChartType = 'pie';
    resourceCountArray: any[];
    resourFormat: any = {};
    resourFormatList: any[] = [];

    resourceEngCountArray: any[];
    resourEngFormat: any = {};
    resourEngFormatList: any[] = [];

    @ViewChild('baseChart') chart: BaseChartDirective;
    // @ViewChild('content') canvas: ElementRef;
    // public download() {

    //     const doc = new jsPDF();
    //     this.content = document.getElementById('content');
    //     console.log('DOWNLOAD WORKS', this.content);
    //     const specialElementHandlers = {
    //         '#editor'(element, renderer) {
    //             return true;
    //         }

    //     };

    //     const content = this.content.nativeElement;
    //     console.log('THIS IS INNER HTML' , this.content.innerHTML);
    //     doc.fromHTML(this.content.innerHTML, 15, 15, {
    //         'width': 190,
    //         'elementHandlers': specialElementHandlers
    //     });

    //     doc.save('test.pdf');
    // }
    @ViewChild('reportContent') reportContent: ElementRef;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        protected projectResourceAssignService: ProjectResourceAssignService,
        private formBuilder: FormBuilder,
        protected activatedRoute: ActivatedRoute,
        protected employeeSalaryService: EmployeeSalaryService
    ) {}

    ngOnInit() {
        this.loadProjectsOnPrms();
        this.getClientsFromSunHrm();
        this.activatedRoute.data.subscribe(({ projectResourceAssign }) => {
            this.projectResourceAssign = projectResourceAssign;
            console.log(this.projectResourceAssign);
        });
        // this.loadRolesForDropDown();
        // this.loadProjectsDropDown();
        this.loadEngineeringUnitsInHrm();
        this.loadSupportUnitsInHrm();

        // this.pieChartLabels.push();
        this.pieChartData.push();
        this.accountService.identity().then(account => {
            this.account = account;
        });

        this.projectResourceAssignForm = this.formBuilder.group({
            id: new FormControl(),
            projectId: new FormControl('', Validators.required),
            fromDate: new FormControl('', Validators.required),
            toDate: new FormControl('', Validators.required),
            billingType: new FormControl('', Validators.required),
            location: new FormControl('', Validators.required),
            estimatedStaffHours: new FormControl('', Validators.required),
            // actualStaffHours: new FormControl('', Validators.required),
            multipleResources: this.formBuilder.array([this.initiateMultipleResources()])
        });
        if (this.projectResourceAssign.id !== undefined) {
            this.loadResourceBasedOnRole(this.projectResourceAssign.role, 0);
            this.loadResourceBasedOnRoleEng(this.projectResourceAssign.role, 0);
            // On updating the project resource to load the values.
            this.projectResourceAssignForm.patchValue({
                id: this.projectResourceAssign.id,
                projectId: this.projectResourceAssign.projectId,
                fromDate: this.projectResourceAssign.fromDate,
                toDate: this.projectResourceAssign.toDate,
                billingType: this.projectResourceAssign.billingType,
                location: this.projectResourceAssign.location,
                estimatedStaffHours: this.projectResourceAssign.estimatedStaffHours
                // actualStaffHours: this.projectResourceAssign.actualStaffHours
            });
            const multipleResourceArray = <FormArray>this.projectResourceAssignForm.get('multipleResources');
            multipleResourceArray.patchValue([
                {
                    role: this.projectResourceAssign.role,
                    empId: this.projectResourceAssign.empId,
                    billValue: this.projectResourceAssign.billValue,
                    percentageOfWork: this.projectResourceAssign.percentageOfWork
                }
            ]);
        }

        // this.registerAuthenticationSuccess();
        // if (this.isAuthenticated) {
        //     this.getResourceCountOnProjectForPieChart();
        // }
        // this.download();
    }

    // getResourceCountOnProjectForPieChart() {
    //     this.accountService.getResourceCountOnProjectForPieChart().subscribe(
    //         (result: any) => {
    //             console.log(result.body);
    //             this.resourceCountArray = result.body;
    //             this.resourceCountArray.forEach( (oneProjectReport, index) => {
    //                 console.log(oneProjectReport.projectName);
    //                 console.log(oneProjectReport.resourceCount);
    //                 this.pieChartLabels.push(oneProjectReport.projectName);
    //                 this.pieChartData.push(oneProjectReport.resourceCount);
    //                  if (this.chart !== undefined) {
    //                     this.chart.ngOnDestroy();
    //                     this.chart.chart = this.chart.getChartBuilder(this.chart.ctx);
    //                 }
    //             });
    //         }
    //     );
    // }

    // registerAuthenticationSuccess() {
    //     this.eventManager.subscribe('authenticationSuccess', message => {
    //         this.accountService.identity().then(account => {
    //             this.account = account;
    //             this.getResourceCountOnProjectForPieChart();
    //         });
    //     });
    // }
    /**
     * To initiate set of arrays.
     */
    initiateMultipleResources() {
        return this.formBuilder.group({
            role: new FormControl('', Validators.required),
            empId: new FormControl('', Validators.required),
            billValue: new FormControl('', Validators.required),
            percentageOfWork: new FormControl('', Validators.required)
        });
    }

    previousState() {
        window.history.back();
    }

    /**
     * To load the sub units in HRM system.
     */

    loadSupportUnitsInHrm() {
        this.projectResourceAssignService.getSubunitsInHrm().subscribe((result: any) => {
            // this.supportArray = result.body;
            this.supportArray = result.body.filter(function(el) {
                return (
                    el['id'] === 8 ||
                    el.id === 18 ||
                    el.id === 11 ||
                    el.id === 12 ||
                    el.id === 10 ||
                    el.id === 26 ||
                    el.id === 17 ||
                    el.id === 19 ||
                    el.id === 21 ||
                    el.id === 4 ||
                    el.id === 5 ||
                    el.id === 20 ||
                    el.id === 2 ||
                    el.id === 1
                );
            });
            // for (let k = 0; k < this.supportArray.length; k++) {
            // console.log('HERRS THE SUPPORT ARRAY', this.supportArray);
            //  this.supportLabels[k] = this.supportArray[k].name;
            // }
            // for (let k = 0; k < this.supportArray.length; k++) {
            //  this.doughnutChartLabels.push(this.supportArray[k].name);
            // }
            console.log('This is REQUIRED Support BODY', this.supportArray);
            for (let i = 0; i < this.supportArray.length; i++) {
                //   this.doughnutChartLabels[i] = this.supportArray[i].name;
                console.log('HERE IS THE LABEL', this.supportArray[i]);
                this.projectResourceAssignService.getEmployeesFromSelectedDepartment(this.supportArray[i].id).subscribe((result: any) => {
                    this.resourcesBasedOnRole = result.body;
                    // console.log('The Resource BASED on ROLE: ', result.body);
                    //  this.demo.splice(index, 0, this.resourcesBasedOnRole);
                    this.resourFormat.name = this.supportArray[i].name;
                    //   console.log('The Resource BASED on Format.name: ', this.resourFormat.name);
                    this.resourFormat[i] = this.resourcesBasedOnRole;
                    //   console.log('The Resource BASED on Format.list: ', this.resourFormat.list);
                    this.resourFormatList.push(this.resourFormat[i]);

                    const nestedObject = {};
                    nestedObject['id'] = this.supportArray[i].id;
                    nestedObject['emp'] = this.resourFormat[i];
                    // nestedObject.id = this.supportArray[i].id;
                    nestedObject['name'] = this.supportArray[i].name;
                    // nestedObject.emp = this. resourFormat[i];
                    // this.newArray.push(this.supportArray[i].id, this.resourFormat[i]);
                    this.newArray.push(nestedObject);
                    // Object.assign(this.supportArray[i].id, this.resourFormat[i] );
                    // console.log('THE INSIDE ARRAY : ', this.newArray);
                    // this.doughnutChartData.push(this.resourFormatList[i].length);
                    // this.SupportTotal = this.SupportTotal + this.resourFormatList[i].length;
                    //   console.log('The Resource BASED on FormatList.push: ', this.resourFormatList);
                });
            }
            // this.doughnutChartData = this.supportLength;
            // console.log('This is Support BODY at  ', this.doughnutChartLabels);
        });
        // console.log('total DATA : ', this.resourFormatList);
        console.log('THE NEW ARRAY : ', this.newArray);
        this.resourceCountArray = this.resourFormatList;
        console.log('Teetotal DATA : ', this.resourceCountArray);
    }

    loadEngineeringUnitsInHrm() {
        this.projectResourceAssignService.getSubunitsInHrm().subscribe((result: any) => {
            // console.log('This is Engineering BODY ', result.body[0]);
            // // this.engineeringArray = result.body;
            this.engineeringArray = result.body.filter(function(el) {
                return el['id'] === 3 || el.id === 9 || el.id === 34 || el.id === 36 || el.id === 6 || el.id === 13;
            });
            // for (let l = 0; l < this.engineeringArray.length; l++) {
            //     this.pieChartLabels.push(this.engineeringArray[l].name);
            //    }
            //    console.log('THE PIECHART ', this.pieChartLabels);

            for (let i = 0; i < this.engineeringArray.length; i++) {
                //   this.doughnutChartLabels[i] = this.supportArray[i].name;
                // console.log('Donut chart LABELS', this.supportArray[i].id);
                this.projectResourceAssignService
                    .getEmployeesFromSelectedDepartment(this.engineeringArray[i].id)
                    .subscribe((result: any) => {
                        this.resourcesBasedOnEngRole = result.body;
                        // console.log('The Resource BASED on ROLE: ', result.body);
                        //  this.demo.splice(index, 0, this.resourcesBasedOnRole);
                        this.resourEngFormat.name = this.engineeringArray[i].name;
                        //   console.log('The Resource BASED on Format.name: ', this.resourFormat.name);
                        this.resourEngFormat[i] = this.resourcesBasedOnEngRole;
                        //   console.log('The Resource BASED on Format.list: ', this.resourFormat.list);
                        this.resourEngFormatList.push(this.resourEngFormat[i]);

                        const nestedObjectEng = {};
                        nestedObjectEng['id'] = this.engineeringArray[i].id;
                        nestedObjectEng['emp'] = this.resourEngFormat[i];
                        nestedObjectEng['name'] = this.engineeringArray[i].name;
                        this.newEngArray.push(nestedObjectEng);
                        // this.pieChartData.push(this.resourEngFormatList.length);
                        // this.EngTotal = this.EngTotal + this.resourEngFormatList.length;
                        //   console.log('The Resource BASED on FormatList.push: ', this.resourFormatList);
                    });
            }
            // console.log('This is Support BODY at Engineering ', this.engineeringArray);

            // this.projectResourceAssignService.getEmployeesFromSelectedDepartment(this.engineeringArray[i].id).subscribe((result: any) => {
            //     this.resourcesBasedOnRole = result.body;
            //     console.log('The Resource BASED on ENGINEERING ROLE: ', result.body);
        });
        console.log('THE NEW ENG ARRAY : ', this.newEngArray);
        this.resourceEngCountArray = this.resourEngFormatList;
    }

    loadEmployeeFromDepartment(selectedDepartment: number) {
        this.employeeSalaryService.getEmployeesFromSelectedDepartment(selectedDepartment).subscribe((result: any) => {
            this.employeesInDepartment = result.body;
        });
    }

    getResourcesOnClient(selectedClient) {
        this.projectResourceAssignService.getResourcesOfClients(selectedClient).subscribe(
            result => {
                this.resourcesOnClients = result.body;
            },
            error => {
                this.resourcesOnClients = [];
            }
        );
    }

    loadProjectsOnPrms() {
        this.projectResourceAssignService.loadProjectsInPrms().subscribe(result => {
            this.projectsInPrms = result.body;
        });
    }

    getClientsFromSunHrm() {
        this.projectResourceAssignService.getClientsFromSunHrm().subscribe(result => {
            this.clientsInHRM = result.body;
        });
    }

    loadResourceBasedOnRole(selectedRole, index) {
        this.projectResourceAssignService.getEmployeesFromSelectedDepartment(selectedRole).subscribe((result: any) => {
            this.resourcesBasedOnRole = result.body;
            this.demo.splice(index, 0, this.resourcesBasedOnRole);
            console.log('This is DEMO: ', this.demo);
            // this.demoLength = this.demo.length;
        });
    }

    loadResourceBasedOnRoleEng(selectedRole, index) {
        this.projectResourceAssignService.getEmployeesFromSelectedDepartment(selectedRole).subscribe((result: any) => {
            this.resourcesBasedOnEngRole = result.body;

            this.demoEng.splice(index, 0, this.resourcesBasedOnEngRole);
        });
    }

    getAddresses(form) {
        return form.get('multipleResources').controls;
    }

    get formData() {
        return this.projectResourceAssignForm.get('multipleResources');
    }

    // getField(index?: number) {
    //     const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
    //     const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
    //     return gettingField;
    // }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

    //     this.specialElementHandlers = {
    //     '#page-heading': (element, renderer) => {
    //         return true;
    //     }
    // };

    //     $('#cmd').click(function() {
    //     this.doc.fromHTML($('#content').html(), 15, 15, {
    //         'width': 170,
    //             'elementHandlers': specialElementHandlers
    //     });
    //     this.doc.save('sample-file.pdf');
    // });

    // download() {
    //     html2canvas(this.screen.nativeElement).then(canvas => {
    //       this.canvas.nativeElement.src = canvas.toDataURL();
    //       this.downloadLink.nativeElement.href = canvas.toDataURL('image/png');
    //       this.downloadLink.nativeElement.download = 'SunTech.png';
    //       this.downloadLink.nativeElement.click();
    //     });
    //   }

    // The html element to become a pdf
    //  let elementToPrint = document.getElementById('page-heading');
    //  this.pdf = new jsPDF('p', 'pt', 'a4');
    //  this.pdf.addHTML(this.elementToPrint, () => {
    //  this.doc.save('web.pdf');
    //  });

    // download() {
    //     const doc = new jspdf();
    //     doc.addHTML(document.getElementById('Print'), function() {
    //     doc.save('obrz.pdf');
    //     });
    // }

    // public download() {
    // this.data = document.getElementById('reportContent');
    // html2canvas(this.data).then(canvas => {
    // // Few necessary setting options
    // this.imgWidth = 208;
    // this.pageHeight = 295;
    // this.imgHeight = canvas.height * this.imgWidth / canvas.width;
    // this.heightLeft = this.imgHeight;

    // const contentDataURL = canvas.toDataURL('image/png');
    // const pdf = new jsPDF('p', 'mm', 'a4'); // A4 size page of PDF
    // this.position = 0;
    // pdf.addImage(contentDataURL, 'PNG', 0, this.position, this.imgWidth, this.imgHeight);
    // pdf.save('MYPdf.pdf'); // Generated PDF
    //     });
    // }

    download() {
        const doc = new jsPDF();
        const specialElementHandlers = {
            '#editor': (element, renderer) => {
                return true;
            }
        };
        const content = this.reportContent.nativeElement;
        doc.fromHTML(content.innerHTML, 15, 15, {
            width: 860,
            elementHandlers: specialElementHandlers
        });

        doc.save('test' + '.pdf');
    }
}
