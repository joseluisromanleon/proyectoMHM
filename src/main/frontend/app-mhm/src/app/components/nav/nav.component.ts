import {AuthService} from '../../services/auth.service';
import {ChangeDetectorRef, Component, HostListener, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {NgbDropdownModule, NgbCollapseModule} from '@ng-bootstrap/ng-bootstrap';
import {NgbModal, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {ModalRegisterComponent} from '../modal-register/modal-register.component';
import {ModalContactComponent} from '../modal-contact/modal-contact.component';
import {ModalLoginComponent} from '../modal-login/modal-login.component';

type AppRole = 'ROLE_ADMIN' | 'ROLE_MECANICO' | 'ROLE_COMERCIAL' | 'ROLE_CLIENTE' | 'ROLE_VISITANTE';

interface MenuItem {
  label: string;
  action: string;
  submenu?: MenuItem[];
}

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    NgForOf,
    NgbDropdownModule, // Solo necesitas el módulo, no los componentes individuales
    NgbCollapseModule,
    NgbModalModule,
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {
  isMobile = window.innerWidth < 765;
  isLoggedIn: boolean = false;
  mainRole?: AppRole;
  username: string = '';
  roles: string[] = [];
  estado: string = '';
  isMenuCollapsed = true;

  menuOptions: Record<AppRole, MenuItem[]> = {
    'ROLE_ADMIN': [
      {
        label: 'Usuarios', action: 'usuarios',
        submenu: [
          {label: 'Usuarios', action: 'usuarios'},
          {label: 'Empleados', action: 'avisos'},
          {label: 'Clientes', action: 'mensajes'},
        ]
      },
      {
        label: 'Productos', action: 'productos',
        submenu: [
          {label: 'Productos', action: 'productos'},
          {label: 'Categorias', action: 'categorias'},
          {label: 'Proveedores', action: 'proveedores'},
        ]
      },
      {
        label: 'Comunicación', action: 'comunicacion',
        submenu: [
          {label: 'Avisos', action: 'avisos'},
          {label: 'Mensajes', action: 'mensajes'},
        ]
      },
      {
        label: 'Administracion', action: 'comunicacion',
        submenu: [
          {label: 'Pedidos', action: 'pedidos'},
          {label: 'Facturas', action: 'facturas'},
        ]
      }
    ],
    'ROLE_MECANICO': [
      {label: 'Mis Avisos', action: 'avisos'},
      {label: 'Mis Rutas', action: 'rutas'},
      {label: 'Mis Clientes', action: 'clientes'},
    ],
    'ROLE_COMERCIAL': [
      {label: 'Mis Mensajes', action: 'Mensajes'},
      {label: 'Mis Rutas', action: 'rutas'},
      {label: 'Mis Clientes', action: 'clientes'},
    ],
    'ROLE_CLIENTE': [
      {label: 'Mis Avisos', action: 'avisos'},
      {label: 'Mis Mensajes', action: 'mensajes'},
      {
        label: 'Panel Documentos', action: 'documentos',
        submenu: [
          {label: 'Pedidos', action: 'pedidos'},
          {label: 'Facturas', action: 'facturas'},
        ]
      },
    ],
    'ROLE_VISITANTE': [
      {label: 'Contactar', action: 'contactar'},
      {label: 'Salir', action: 'logout'},
    ]
  };

  constructor(private authService: AuthService,
              private cdr: ChangeDetectorRef,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(isLogged => {
      this.isLoggedIn = isLogged;
      if (isLogged) {
        this.roles = this.authService.getRoles();
        this.username = this.authService.getName();
        this.estado = this.authService.getEstado();

        if (this.roles.length > 0) {
          const firstRole = this.roles[0];
          this.mainRole = Object.keys(this.menuOptions).includes(firstRole)
            ? firstRole as AppRole
            : undefined;
        } else {
          this.logout();
        }
      } else {
        this.roles = [];
        this.estado = '';
        this.username = '';
        this.mainRole = undefined;
      }
      this.cdr.detectChanges();
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile = window.innerWidth < 765;
  }

  openLoginModal() {
    this.modalService.open(ModalLoginComponent, {centered: true});
  }

  openRegisterModal() {
    this.modalService.open(ModalRegisterComponent, {centered:true});
  }

  openContactModal(){
    this.modalService.open(ModalContactComponent, {centered:true});
  }

  logout() {
    this.authService.logout();
    this.roles = [];
    this.estado = '';
    this.username = '';
    this.mainRole = undefined;
    this.isLoggedIn = false;
  }
}
