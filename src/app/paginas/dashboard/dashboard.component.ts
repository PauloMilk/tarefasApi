import { Component, OnInit } from '@angular/core';
import { EventEmitterService } from 'src/app/services/eventEmitter.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public selected = 'TODO';
  constructor() { }

  ngOnInit() {
  }
}