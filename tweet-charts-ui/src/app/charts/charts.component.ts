import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ChartsService } from '../services/charts.service';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
  private username: any;

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    console.log('Requesting charts for', this.username);

    this.chartService.getUserProfile(this.username)
    .subscribe(result => {
      console.log(result);
    });;
  }
}
