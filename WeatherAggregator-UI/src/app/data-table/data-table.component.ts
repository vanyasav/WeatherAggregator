import {Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { DataTableDataSource, DataTableItem } from './data-table-datasource';
import {DataService} from '../data.service';
import {takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';

interface Cities {
  name: string;
  sys:
    {
      country: string;
    };
  coord:
    {
      lat: number;
      lon: number;
    };
  description: string;
  timezone: number;
}

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<DataTableItem>;
  dataSource: DataTableDataSource;
  cityName = 'default';
  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['time', 'clouds.all', 'main.temp', 'main.feels_like', 'main.humidity', 'main.pressure', 'visibility', 'description'];
  destroy$: Subject<boolean> = new Subject<boolean>();
  data: any [];
  CitiesList: Cities [];
  minDate1: Date;
  maxDate: Date;
  minDate2: Date;
  startDate: Date;
  endDate: Date;
  disableSelect: boolean;
  testData: any [];
  testData2: any [];
  time: number;
  timezone: number;
  testArray: any[];
  timezoneMap = new Map();
  serverTimeZone: number;
  citySelected: boolean;
  firstDateSelected: boolean;
  secondDateSelected: boolean;

  testnumber: number;
  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.serverTimeZone = 10800000;
    this.citySelected = false;
    this.firstDateSelected = false;
    this.secondDateSelected = false;
    this.timezoneMap.set(0, 'UTC');
    this.timezoneMap.set(3600, 'UTC+1');
    this.timezoneMap.set(7200, 'UTC+2');
    this.timezoneMap.set(10800, 'UTC+3');
    this.timezoneMap.set(12600, 'UTC+3:30');
    this.timezoneMap.set(14400, 'UTC+4');
    this.timezoneMap.set(16200, 'UTC+4:30');
    this.timezoneMap.set(18000, 'UTC+5');
    this.timezoneMap.set(19800, 'UTC+5:30');
    this.timezoneMap.set(20700, 'UTC+4:45');
    this.timezoneMap.set(21600, 'UTC+6');
    this.timezoneMap.set(23400, 'UTC+6:30');
    this.timezoneMap.set(25200, 'UTC+7');
    this.timezoneMap.set(28800, 'UTC+8');
    this.timezoneMap.set(30420, 'UTC+8:45');
    this.timezoneMap.set(32400, 'UTC+9');
    this.timezoneMap.set(34200, 'UTC+9:30');
    this.timezoneMap.set(36000, 'UTC+10');
    this.timezoneMap.set(37800, 'UTC+10:30');
    this.timezoneMap.set(39600, 'UTC+11');
    this.timezoneMap.set(43200, 'UTC+12');
    this.timezoneMap.set(49500, 'UTC+12:45');
    this.timezoneMap.set(46800, 'UTC+13');
    this.timezoneMap.set(50400, 'UTC+14');
    this.timezoneMap.set(-3600, 'UTC-1');
    this.timezoneMap.set(-7200, 'UTC-2');
    this.timezoneMap.set(-10800, 'UTC-3');
    this.timezoneMap.set(-12600, 'UTC-3:30');
    this.timezoneMap.set(-14400, 'UTC-4');
    this.timezoneMap.set(-18000, 'UTC-5');
    this.timezoneMap.set(-21600, 'UTC-6');
    this.timezoneMap.set(-25200, 'UTC-7');
    this.timezoneMap.set(-28800, 'UTC-8');
    this.timezoneMap.set(-32400, 'UTC-9');
    this.timezoneMap.set(-34200, 'UTC-9:30');
    this.timezoneMap.set(-36000, 'UTC-10');
    this.timezoneMap.set(-39600, 'UTC-11');
    this.timezoneMap.set(-43200, 'UTC-12');

    this.disableSelect = true;
    this.dataService.sendGetRequest().pipe(takeUntil(this.destroy$)).subscribe((data: any[]) => {
      this.data = data;
      console.log(data);
    });

    this.dataService.sendGetCitiesRequest().pipe(takeUntil(this.destroy$)).subscribe((data: any[]) => {
      this.CitiesList = data.sort();
      // tslint:disable-next-line:only-arrow-functions
      this.CitiesList.sort(function(a, b) {
        if (a.name > b.name) {
          return 1;
        } else { return -1; }
      });
    });
  }

  onSelectionChange() {
    // После выбора города фильтруем данные по имени города, затем переводим время в epochTime и находим минимальное
    this.testData = this.data.filter(data => data.name === this.cityName);
    this.testData2 = this.testData.map(testData => testData.epochTime = Date.parse(testData.time));
    this.time = Math.min(...this.testData2);
    this.testArray = [...new Set(this.testData.map(testData => testData.timezone))];
    this.time = this.time + this.testArray[0] * 1000 - this.serverTimeZone;
    this.minDate1 = new Date(this.time);
    this.minDate2 = new Date(this.time);
    this.time = Math.max(...this.testData2);
    this.maxDate = new Date(this.time + this.testArray[0] * 1000 - this.serverTimeZone);
    this.citySelected = true;
  }

  OnDateChange(value: Date) {
    this.startDate = new Date(value.getTime() + this.serverTimeZone);
    this.firstDateSelected = true;
  }

  OnDateChange2(value: Date) {
    this.endDate = new Date(value.getTime() + this.serverTimeZone);
    this.secondDateSelected = true;
  }

  OnButtonClicked() {
    if (this.citySelected && this.firstDateSelected && this.firstDateSelected === true) {
      this.dataSource = new DataTableDataSource(this.data, this.cityName, this.startDate, this.endDate);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
    } else { window.alert('ТЕСТ'); }

  }
}
