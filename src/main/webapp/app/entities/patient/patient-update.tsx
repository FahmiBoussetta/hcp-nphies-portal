import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PatientUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const contacts = useAppSelector(state => state.contact.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const patientEntity = useAppSelector(state => state.patient.entity);
  const loading = useAppSelector(state => state.patient.loading);
  const updating = useAppSelector(state => state.patient.updating);
  const updateSuccess = useAppSelector(state => state.patient.updateSuccess);

  const handleClose = () => {
    props.history.push('/patient');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getContacts({}));
    dispatch(getAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.birthDate = convertDateTimeToServer(values.birthDate);
    values.deceasedDate = convertDateTimeToServer(values.deceasedDate);

    const entity = {
      ...patientEntity,
      ...values,
      contacts: contacts.find(it => it.id.toString() === values.contactsId.toString()),
      address: addresses.find(it => it.id.toString() === values.addressId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          birthDate: displayDefaultDateTime(),
          deceasedDate: displayDefaultDateTime(),
        }
      : {
          ...patientEntity,
          religion: 'N0',
          gender: 'Male',
          birthDate: convertDateTimeFromServer(patientEntity.birthDate),
          deceasedDate: convertDateTimeFromServer(patientEntity.deceasedDate),
          maritalStatus: 'L',
          contactsId: patientEntity?.contacts?.id,
          addressId: patientEntity?.address?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="patient-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.guid')}
                id="patient-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.forceId')}
                id="patient-forceId"
                name="forceId"
                data-cy="forceId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.residentNumber')}
                id="patient-residentNumber"
                name="residentNumber"
                data-cy="residentNumber"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.passportNumber')}
                id="patient-passportNumber"
                name="passportNumber"
                data-cy="passportNumber"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.nationalHealthId')}
                id="patient-nationalHealthId"
                name="nationalHealthId"
                data-cy="nationalHealthId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.iqama')}
                id="patient-iqama"
                name="iqama"
                data-cy="iqama"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.religion')}
                id="patient-religion"
                name="religion"
                data-cy="religion"
                type="select"
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
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.gender')}
                id="patient-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                <option value="Male">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Male')}</option>
                <option value="Female">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Female')}</option>
                <option value="Unknown">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.Unknown')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.U')}</option>
                <option value="N">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.N')}</option>
                <option value="A">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.A')}</option>
                <option value="B">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.B')}</option>
                <option value="C">{translate('hcpNphiesPortalApp.AdministrativeGenderEnum.C')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.birthDate')}
                id="patient-birthDate"
                name="birthDate"
                data-cy="birthDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.deceasedDate')}
                id="patient-deceasedDate"
                name="deceasedDate"
                data-cy="deceasedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.patient.maritalStatus')}
                id="patient-maritalStatus"
                name="maritalStatus"
                data-cy="maritalStatus"
                type="select"
              >
                <option value="L">{translate('hcpNphiesPortalApp.MaritalStatusEnum.L')}</option>
                <option value="D">{translate('hcpNphiesPortalApp.MaritalStatusEnum.D')}</option>
                <option value="M">{translate('hcpNphiesPortalApp.MaritalStatusEnum.M')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.MaritalStatusEnum.U')}</option>
                <option value="W">{translate('hcpNphiesPortalApp.MaritalStatusEnum.W')}</option>
                <option value="UNK">{translate('hcpNphiesPortalApp.MaritalStatusEnum.UNK')}</option>
              </ValidatedField>
              <ValidatedField
                id="patient-contacts"
                name="contactsId"
                data-cy="contacts"
                label={translate('hcpNphiesPortalApp.patient.contacts')}
                type="select"
              >
                <option value="" key="0" />
                {contacts
                  ? contacts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="patient-address"
                name="addressId"
                data-cy="address"
                label={translate('hcpNphiesPortalApp.patient.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient" replace color="info">
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
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PatientUpdate;
