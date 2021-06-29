import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './patient.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PatientDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const patientEntity = useAppSelector(state => state.patient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.patient.detail.title">Patient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{patientEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.patient.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{patientEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.patient.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{patientEntity.forceId}</dd>
          <dt>
            <span id="residentNumber">
              <Translate contentKey="hcpNphiesPortalApp.patient.residentNumber">Resident Number</Translate>
            </span>
          </dt>
          <dd>{patientEntity.residentNumber}</dd>
          <dt>
            <span id="passportNumber">
              <Translate contentKey="hcpNphiesPortalApp.patient.passportNumber">Passport Number</Translate>
            </span>
          </dt>
          <dd>{patientEntity.passportNumber}</dd>
          <dt>
            <span id="nationalHealthId">
              <Translate contentKey="hcpNphiesPortalApp.patient.nationalHealthId">National Health Id</Translate>
            </span>
          </dt>
          <dd>{patientEntity.nationalHealthId}</dd>
          <dt>
            <span id="iqama">
              <Translate contentKey="hcpNphiesPortalApp.patient.iqama">Iqama</Translate>
            </span>
          </dt>
          <dd>{patientEntity.iqama}</dd>
          <dt>
            <span id="religion">
              <Translate contentKey="hcpNphiesPortalApp.patient.religion">Religion</Translate>
            </span>
          </dt>
          <dd>{patientEntity.religion}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="hcpNphiesPortalApp.patient.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{patientEntity.gender}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="hcpNphiesPortalApp.patient.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>{patientEntity.birthDate ? <TextFormat value={patientEntity.birthDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deceasedDate">
              <Translate contentKey="hcpNphiesPortalApp.patient.deceasedDate">Deceased Date</Translate>
            </span>
          </dt>
          <dd>
            {patientEntity.deceasedDate ? <TextFormat value={patientEntity.deceasedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="maritalStatus">
              <Translate contentKey="hcpNphiesPortalApp.patient.maritalStatus">Marital Status</Translate>
            </span>
          </dt>
          <dd>{patientEntity.maritalStatus}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.patient.contacts">Contacts</Translate>
          </dt>
          <dd>{patientEntity.contacts ? patientEntity.contacts.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.patient.address">Address</Translate>
          </dt>
          <dd>{patientEntity.address ? patientEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient/${patientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientDetail;
