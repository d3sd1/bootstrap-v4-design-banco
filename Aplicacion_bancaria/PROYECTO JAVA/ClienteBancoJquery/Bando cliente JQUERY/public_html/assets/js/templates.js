var templateCliente = `<li>
                                <div class="collapsible-header"><i class="material-icons">person_outline</i>{{dni}}</div>
                                <div class="collapsible-body">
                                    <span>
                                        <div class="row">
                                            <form class="col s12" data-client="true">
                                              <div class="row">
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">credit_card</i>
                                                  <input value="{{dni}}" name="dni" class="validate" autocomplete="off" type="text" readonly>
                                                  <label>DNI</label>
                                                </div>
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">credit_card</i>
                                                  <input value="{{nombre}}" name="nombre" class="validate" autocomplete="off" type="text"{{found}}>
                                                  <label>Nombre</label>
                                                </div>
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">streetview</i>
                                                  <input value="{{address}}" name="direccion" class="validate" autocomplete="off" type="text"{{found}}>
                                                  <label>Dirección</label>
                                                </div>
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">phone</i>
                                                  <input value="{{telefono}}" name="telefono" class="validate" autocomplete="off" type="text"{{found}}>
                                                  <label>Teléfono</label>
                                                </div>
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">email</i>
                                                  <input value="{{email}}" name="email" autocomplete="off" class="validate" type="email"{{found}}>
                                                  <label>Email</label>
                                                </div>
                                                <div class="input-field col s12 m4">
                                                  <i class="material-icons prefix">date_range</i>
                                                  <input value="{{birth_date}}" name="fecha_nacimiento" class="datepicker validate" autocomplete="off" type="text"{{found}}>
                                                  <label>Fecha nacimiento</label>
                                                </div>
                                              </div>
                                            </form>
                                        </div>
                                    </span>
                                </div>
                        </li>`,
        templateUser = `<li>
        <div class="collapsible-header"><i class="material-icons">person</i>{{cliente_nombre}}</div>
        <div class="collapsible-body">
            <span>
                <div class="row">
                    <form class="col s12">
                      <div class="row">
                        <div class="input-field col s6">
                          <i class="material-icons prefix">credit_card</i>
                          <input value="{{dni}}" type="text" readonly>
                          <label>DNI</label>
                        </div>
                        <div class="input-field col s6">
                          <i class="material-icons prefix">contact_phone</i>
                          <input value="{{telefono}}" type="text" readonly>
                          <label>Teléfono</label>
                        </div>
                      </div>
                    </form>
                </div>
            </span>
        </div>
    </li>`;