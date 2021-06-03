import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './diagnosis-sequence.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDiagnosisSequenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DiagnosisSequenceDetail = (props: IDiagnosisSequenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { diagnosisSequenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="diagnosisSequenceDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.detail.title">DiagnosisSequence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{diagnosisSequenceEntity.id}</dd>
          <dt>
            <span id="diagSeq">
              <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.diagSeq">Diag Seq</Translate>
            </span>
          </dt>
          <dd>{diagnosisSequenceEntity.diagSeq}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.item">Item</Translate>
          </dt>
          <dd>{diagnosisSequenceEntity.item ? diagnosisSequenceEntity.item.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/diagnosis-sequence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/diagnosis-sequence/${diagnosisSequenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ diagnosisSequence }: IRootState) => ({
  diagnosisSequenceEntity: diagnosisSequence.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DiagnosisSequenceDetail);
