import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MensajeService} from "../mensaje.service";
import {Router} from "@angular/router";

@Component({
  selector: 'mensaje-create',
  templateUrl: './mensaje-create.component.html',
  styleUrls: ['./mensaje-create.component.css']
})
export class MensajeCreateComponent implements OnInit {

  form: FormGroup;

  constructor(
    public mensajeService: MensajeService,
    private router: Router,
    private fb:FormBuilder
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required,
        //Validators.pattern(/^[a-zA-Z\s]+$/)
      ]], // Solo letras

    });
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
    this.mensajeService.create(this.form.value).subscribe(res => {
      console.log('Mensaje creado correctamente! + res');
      this.router.navigateByUrl('mensaje/index').then();
    })
  }

}
