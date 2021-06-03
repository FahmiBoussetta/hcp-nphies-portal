import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPatientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PatientUpdate = (props: IPatientUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { patientEntity, contacts, addresses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/patient');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getContacts();
    props.getAddresses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.birthDate = convertDateTimeToServer(values.birthDate);
    values.deceasedDate = convertDateTimeToServer(values.deceasedDate);

    if (errors.length === 0) {
      const entity = {
        ...patientEntity,
        ...values,
        contacts: contacts.find(it => it.id.toString() === values.contactsId.toString()),
        address: addresses.find(it => it.id.toString() === values.addressId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.patient.home.createOrEditLabel">Create or edit a Patient</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : patientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="patient-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="patient-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="patient-guid">
                  <Translate contentKey="hcpNphiesPortalApp.patient.guid">Guid</Translate>
                </Label>
                <AvField id="patient-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="patient-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.patient.forceId">Force Id</Translate>
                </Label>
                <AvField id="patient-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="residentNumberLabel" for="patient-residentNumber">
                  <Translate contentKey="hcpNphiesPortalApp.patient.residentNumber">Resident Number</Translate>
                </Label>
                <AvField id="patient-residentNumber" data-cy="residentNumber" type="text" name="residentNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="passportNumberLabel" for="patient-passportNumber">
                  <Translate contentKey="hcpNphiesPortalApp.patient.passportNumber">Passport Number</Translate>
                </Label>
                <AvField id="patient-passportNumber" data-cy="passportNumber" type="text" name="passportNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="nationalHealthIdLabel" for="patient-nationalHealthId">
                  <Translate contentKey="hcpNphiesPortalApp.patient.nationalHealthId">National Health Id</Translate>
                </Label>
                <AvField id="patient-nationalHealthId" data-cy="nationalHealthId" type="text" name="nationalHealthId" />
              </AvGroup>
              <AvGroup>
                <Label id="iqamaLabel" for="patient-iqama">
                  <Translate contentKey="hcpNphiesPortalApp.patient.iqama">Iqama</Translate>
                </Label>
                <AvField id="patient-iqama" data-cy="iqama" type="text" name="iqama" />
              </AvGroup>
              <AvGroup>
                <Label id="religionLabel" for="patient-religion">
                  <Translate contentKey="hcpNphiesPortalApp.patient.religion">Religion</Translate>
                </Label>
                <AvInput
                  id="patient-religion"
                  data-cy="religion"
                  type="select"
                  className="form-control"
                  name="religion"
                  value={(!isNew && patientEntity.religion) || 'N0'}
                >
                  <option value="N0">{translate('hcpNphiesPortalApp.ReligionEnum.N0')}</option>
                  <option value="N1">{translate('hcpNphiesPortalApp.ReligionEnum.N1')}</option>
                  <option value="N2">{translate('hcpNphiesPortalApp.ReligionEnum.N2')}</option>
                  <option value="N3">{translate('hcpNphiesPortalApp.ReligionEnum.N3')}</option>
                  <option value="N4">{translate('hcpNphiesPortalApp.ReligionEnum.N4')}</option>
                  <option value="N5">{translate('hcpNphiesPortalApp.ReligionEnum.N5')}</option>
                  <option value="N7">{translate('hcpNphiesPortalApp.ReligionEnum.N7')}</option>
                  <option value="N8">{translate('hcpNphiesPortalApp.ReligionEnum.N8')}</option>
                  <option value="N9">{translate('hcpNphiesPortalApp.ReligionEnum.N9')}</option>
                  <option value="N98">{translate('hcpNphiesPortalApp.ReligionEnum.N98')}</option>
                  <option value="N99">{translate('hcpNphiesPortalApp.ReligionEnum.N99')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="patient-gender">
                  <Translate contentKey="hcpNphiesPortalApp.patient.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="patient-gender"
                  data-cy="gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && patientEntity.gender) || 'Male'}
                >
                  <option value="Male">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Male')}</option>
                  <option value="Female">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Female')}</option>
                  <option value="Unknown">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Unknown')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.U')}</option>
                  <option value="N">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.N')}</option>
                  <option value="A">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.A')}</option>
                  <option value="B">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.B')}</option>
                  <option value="C">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.C')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="birthDateLabel" for="patient-birthDate">
                  <Translate contentKey="hcpNphiesPortalApp.patient.birthDate">Birth Date</Translate>
                </Label>
                <AvInput
                  id="patient-birthDate"
                  data-cy="birthDate"
                  type="datetime-local"
                  className="form-control"
                  name="birthDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.patientEntity.birthDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deceasedDateLabel" for="patient-deceasedDate">
                  <Translate contentKey="hcpNphiesPortalApp.patient.deceasedDate">Deceased Date</Translate>
                </Label>
                <AvInput
                  id="patient-deceasedDate"
                  data-cy="deceasedDate"
                  type="datetime-local"
                  className="form-control"
                  name="deceasedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.patientEntity.deceasedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="maritalStatusLabel" for="patient-maritalStatus">
                  <Translate contentKey="hcpNphiesPortalApp.patient.maritalStatus">Marital Status</Translate>
                </Label>
                <AvInput
                  id="patient-maritalStatus"
                  data-cy="maritalStatus"
                  type="select"
                  className="form-control"
                  name="maritalStatus"
                  value={(!isNew && patientEntity.maritalStatus) || 'L'}
                >
                  <option value="L">{translate('hcpNphiesPortalApp.MaritalStatusEnum.L')}</option>
                  <option value="D">{translate('hcpNphiesPortalApp.MaritalStatusEnum.D')}</option>
                  <option value="M">{translate('hcpNphiesPortalApp.MaritalStatusEnum.M')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.MaritalStatusEnum.U')}</option>
                  <option value="W">{translate('hcpNphiesPortalApp.MaritalStatusEnum.W')}</option>
                  <option value="UNK">{translate('hcpNphiesPortalApp.MaritalStatusEnum.UNK')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="patient-contacts">
                  <Translate contentKey="hcpNphiesPortalApp.patient.contacts">Contacts</Translate>
                </Label>
                <AvInput id="patient-contacts" data-cy="contacts" type="select" className="form-control" name="contactsId">
                  <option value="" key="0" />
                  {contacts
                    ? contacts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="patient-address">
                  <Translate contentKey="hcpNphiesPortalApp.patient.address">Address</Translate>
                </Label>
                <AvInput id="patient-address" data-cy="address" type="select" className="form-control" name="addressId">
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/patient" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  contacts: storeState.contact.entities,
  addresses: storeState.address.entities,
  patientEntity: storeState.patient.entity,
  loading: storeState.patient.loading,
  updating: storeState.patient.updating,
  updateSuccess: storeState.patient.updateSuccess,
});

const mapDispatchToProps = {
  getContacts,
  getAddresses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PatientUpdate);
