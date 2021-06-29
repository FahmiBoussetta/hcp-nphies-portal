import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './response-insurance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsuranceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const responseInsuranceEntity = useAppSelector(state => state.responseInsurance.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="responseInsuranceDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.responseInsurance.detail.title">ResponseInsurance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceEntity.id}</dd>
          <dt>
            <span id="notInforceReason">
              <Translate contentKey="hcpNphiesPortalApp.responseInsurance.notInforceReason">Not Inforce Reason</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceEntity.notInforceReason}</dd>
          <dt>
            <span id="inforce">
              <Translate contentKey="hcpNphiesPortalApp.responseInsurance.inforce">Inforce</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceEntity.inforce ? 'true' : 'false'}</dd>
          <dt>
            <span id="benefitStart">
              <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitStart">Benefit Start</Translate>
            </span>
          </dt>
          <dd>
            {responseInsuranceEntity.benefitStart ? (
              <TextFormat value={responseInsuranceEntity.benefitStart} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="benefitEnd">
              <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitEnd">Benefit End</Translate>
            </span>
          </dt>
          <dd>
            {responseInsuranceEntity.benefitEnd ? (
              <TextFormat value={responseInsuranceEntity.benefitEnd} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverage">Coverage</Translate>
          </dt>
          <dd>{responseInsuranceEntity.coverage ? responseInsuranceEntity.coverage.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverageEligibilityResponse">
              Coverage Eligibility Response
            </Translate>
          </dt>
          <dd>{responseInsuranceEntity.coverageEligibilityResponse ? responseInsuranceEntity.coverageEligibilityResponse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/response-insurance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/response-insurance/${responseInsuranceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResponseInsuranceDetail;
