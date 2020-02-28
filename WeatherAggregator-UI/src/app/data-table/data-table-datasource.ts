import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge } from 'rxjs';


export interface DataTableItem {
  unique_id: number;
  name: string;
  clouds: {
    all: number;
  };
  coord:
    {
      lon: string,
      lat: string
    };
  main: {
    temp: number,
    feels_like: number,
    pressure: number,
    humidity: number,
  };
  visibility: number;
  description: string;
  utc: string;
  time: string;
  epochTime: number;
}


/**
 * Data source for the DataTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class DataTableDataSource extends DataSource<DataTableItem> {
  data: DataTableItem[];
  paginator: MatPaginator;
  sort: MatSort;
  constructor(data: any[], city: string, startDate: Date, endDate: Date) {
    super();
    // tslint:disable-next-line:max-line-length
    this.data = data.filter(data => data.name === city &&  (startDate.getTime() - data.timezone * 1000 <= data.epochTime) && (endDate.getTime() + 86400000 - data.timezone * 1000 >= data.epochTime));

  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<DataTableItem[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    const dataMutations = [
      observableOf(this.data),
      this.paginator.page,
      this.sort.sortChange
    ];

    return merge(...dataMutations).pipe(map(() => {
      return this.getPagedData(this.getSortedData([...this.data]));
    }));
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {}

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: DataTableItem[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: DataTableItem[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'clouds.all': return compare(+a.clouds.all, +b.clouds.all, isAsc);
        case 'main.temp': return compare(+a.main.temp, +b.main.temp, isAsc);
        case 'main.feels_like': return compare(+a.main.feels_like, +b.main.feels_like, isAsc);
        case 'main.pressure': return compare(+a.main.pressure, +b.main.pressure, isAsc);
        case 'main.humidity': return compare(+a.main.humidity, +b.main.humidity, isAsc);
        case 'visibility': return compare(+a.visibility, +b.visibility, isAsc);
        case 'description': return compare(a.description, b.description, isAsc);
        case 'time': return compare(a.time, b.time, isAsc);
        default: return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a, b, isAsc) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
