import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaIndexComponent } from './categoria/index/categoria-index.component';

describe('IndexComponent', () => {
  let component: CategoriaIndexComponent;
  let fixture: ComponentFixture<CategoriaIndexComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriaIndexComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriaIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
