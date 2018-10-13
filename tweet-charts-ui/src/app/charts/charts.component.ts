import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
  private username: any;

  constructor(private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    console.log('hellooo ' + this.username);
  }
}
