import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CategoriaService} from "../categoria.service";
import {Router} from "@angular/router";

@Component({
  selector: 'categoria-create',
  templateUrl: './categoria-create.component.html',
  styleUrls: ['./categoria-create.component.css']
})
export class CategoriaCreateComponent implements OnInit {

  form: FormGroup;

  constructor(
    public categoriaService: CategoriaService,
    private router: Router,
    private fb:FormBuilder
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/)]], // Solo letras

    });
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
    this.categoriaService.create(this.form.value).subscribe(res => {
      console.log('Categoría creada correctamente! + res');
      this.router.navigateByUrl('categoria/index').then();
    })
  }

}
