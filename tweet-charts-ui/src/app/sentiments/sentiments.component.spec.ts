import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SentimentsComponent } from './sentiments.component';

describe('SentimentsComponent', () => {
  let component: SentimentsComponent;
  let fixture: ComponentFixture<SentimentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SentimentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SentimentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
