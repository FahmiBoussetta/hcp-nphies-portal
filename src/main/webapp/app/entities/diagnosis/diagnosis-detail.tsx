import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './diagnosis.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DiagnosisDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const diagnosisEntity = useAppSelector(state => state.diagnosis.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="diagnosisDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.diagnosis.detail.title">Diagnosis</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{diagnosisEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.diagnosis.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{diagnosisEntity.sequence}</dd>
          <dt>
            <span id="diagnosis">
              <Translate contentKey="hcpNphiesPortalApp.diagnosis.diagnosis">Diagnosis</Translate>
            </span>
          </dt>
          <dd>{diagnosisEntity.diagnosis}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.diagnosis.type">Type</Translate>
            </span>
          </dt>
          <dd>{diagnosisEntity.type}</dd>
          <dt>
            <span id="onAdmission">
              <Translate contentKey="hcpNphiesPortalApp.diagnosis.onAdmission">On Admission</Translate>
            </span>
          </dt>
          <dd>{diagnosisEntity.onAdmission}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.diagnosis.claim">Claim</Translate>
          </dt>
          <dd>{diagnosisEntity.claim ? diagnosisEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/diagnosis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/diagnosis/${diagnosisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiagnosisDetail;
