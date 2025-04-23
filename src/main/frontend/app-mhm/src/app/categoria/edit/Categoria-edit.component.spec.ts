import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaEditComponent } from './Categoria-edit.component';

describe('EditComponent', () => {
  let component: CategoriaEditComponent;
  let fixture: ComponentFixture<CategoriaEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriaEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriaEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
